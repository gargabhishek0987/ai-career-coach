package com.aicoach.app.security;

import com.aicoach.app.model.User;
import com.aicoach.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Value("${app.oauth2.redirect-uri}")
    private String redirectUri;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // Extract email and name from OAuth2 provider
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        // Handle GitHub which may not return email directly
        if (email == null) {
            email = oAuth2User.getAttribute("login") + "@github.com";
        }
        if (name == null) {
            name = oAuth2User.getAttribute("login");
        }

        // Determine provider
        String provider = "GOOGLE";
        if (request.getRequestURI().contains("github")) {
            provider = "GITHUB";
        }

        // Check if user exists, if not create them automatically
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (!existingUser.isPresent()) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setProfilePicture(picture);
            newUser.setProvider(provider);
            newUser.setRole("USER");
            newUser.setCreatedAt(LocalDateTime.now());
            userRepository.save(newUser);
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(email);

        // Redirect to Angular with token
        String targetUrl = redirectUri + "?token=" + token + "&email=" + email + "&name=" + name;
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}

package com.aicoach.app.service;

import com.aicoach.app.model.User;
import com.aicoach.app.repository.UserRepository;
import com.aicoach.app.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private AuthenticationManager authenticationManager;

    public Map<String, Object> register(String email, String password, String name) {
        Map<String, Object> response = new HashMap<>();

        if (userRepository.existsByEmail(email)) {
            response.put("status", "error");
            response.put("message", "Email already registered. Please login.");
            return response;
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setProvider("LOCAL");
        user.setRole("USER");
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        String token = jwtUtil.generateToken(email);
        response.put("status", "success");
        response.put("message", "Registration successful!");
        response.put("token", token);
        response.put("email", email);
        response.put("name", name);
        return response;
    }

    public Map<String, Object> login(String email, String password) {
        Map<String, Object> response = new HashMap<>();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtUtil.generateToken(email);
            response.put("status", "success");
            response.put("message", "Login successful!");
            response.put("token", token);
            response.put("email", email);
            response.put("name", user.getName());
            response.put("provider", user.getProvider());
            response.put("profilePicture", user.getProfilePicture());
            return response;
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Invalid email or password.");
            return response;
        }
    }

    public Map<String, Object> getProfile(String email) {
        Map<String, Object> response = new HashMap<>();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        response.put("email", user.getEmail());
        response.put("name", user.getName());
        response.put("provider", user.getProvider());
        response.put("profilePicture", user.getProfilePicture());
        response.put("role", user.getRole());
        response.put("createdAt", user.getCreatedAt().toString());
        return response;
    }
}

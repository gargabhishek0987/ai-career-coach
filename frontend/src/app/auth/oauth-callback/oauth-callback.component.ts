import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../shared/services/auth.service';

@Component({
  selector: 'app-oauth-callback',
  template: `
    <div style="display:flex;align-items:center;justify-content:center;
                height:100vh;flex-direction:column;gap:16px;">
      <div style="width:36px;height:36px;border:3px solid #e0e7ff;
                  border-top-color:#4f46e5;border-radius:50%;
                  animation:spin 0.7s linear infinite;"></div>
      <p style="color:#6b7280;font-size:15px;">Completing login, please wait...</p>
      <style>@keyframes spin { to { transform: rotate(360deg); } }</style>
    </div>
  `
})
export class OauthCallbackComponent implements OnInit {

  constructor(
      private route: ActivatedRoute,
      private router: Router,
      private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const token = params['token'];
      const email = params['email'];
      const name  = params['name'];

      if (token && email) {
        this.authService.saveToken(token, email, name || email);
        this.router.navigate(['/dashboard']);
      } else {
        this.router.navigate(['/login']);
      }
    });
  }
}
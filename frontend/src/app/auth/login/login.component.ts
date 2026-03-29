import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../shared/services/auth.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email    = '';
  password = '';
  loading  = false;
  error    = '';

  constructor(private authService: AuthService, private router: Router) {
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/dashboard']);
    }
  }

  login(): void {
    if (!this.email || !this.password) {
      this.error = 'Please enter email and password.';
      return;
    }
    this.loading = true;
    this.error   = '';

    this.authService.login({ email: this.email, password: this.password })
      .subscribe({
        next: (res: any) => {
          this.loading = false;
          if (res.status === 'success') {
            this.authService.saveToken(res.token, res.email, res.name);
            this.router.navigate(['/dashboard']);
          } else {
            this.error = res.message;
          }
        },
        error: () => {
          this.loading = false;
          this.error   = 'Invalid email or password.';
        }
      });
  }

  loginWithGoogle(): void  { this.authService.loginWithGoogle(); }
  loginWithGithub(): void  { this.authService.loginWithGithub(); }
}

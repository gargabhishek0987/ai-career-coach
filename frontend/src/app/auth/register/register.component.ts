import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../shared/services/auth.service';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  name     = '';
  email    = '';
  password = '';
  loading  = false;
  error    = '';

  constructor(private authService: AuthService, private router: Router) {
    if (this.authService.isLoggedIn()) {
      this.router.navigate(['/dashboard']);
    }
  }

  register(): void {
    if (!this.name || !this.email || !this.password) {
      this.error = 'All fields are required.';
      return;
    }
    if (this.password.length < 6) {
      this.error = 'Password must be at least 6 characters.';
      return;
    }
    this.loading = true;
    this.error   = '';

    this.authService.register({ name: this.name, email: this.email, password: this.password })
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
          this.error   = 'Registration failed. Please try again.';
        }
      });
  }

  loginWithGoogle(): void { this.authService.loginWithGoogle(); }
  loginWithGithub(): void { this.authService.loginWithGithub(); }
}

import { Component } from '@angular/core';
import { CoachService } from '../../shared/services/coach.service';

@Component({
  selector: 'app-job-roles',
  templateUrl: './job-roles.component.html',
  styleUrls: ['./coach-shared.css']
})
export class JobRolesComponent {
  loading  = false;
  error    = '';
  response = '';

  constructor(private coachService: CoachService) {}

  getSuggestions(): void {
    this.loading  = true;
    this.error    = '';
    this.response = '';

    this.coachService.suggestJobRoles().subscribe({
      next: (res: any) => {
        this.loading  = false;
        if (res.status === 'error') { this.error = res.message; return; }
        this.response = res.aiResponse;
      },
      error: () => {
        this.loading = false;
        this.error   = 'Failed to get suggestions. Make sure you have uploaded your resume first.';
      }
    });
  }
}

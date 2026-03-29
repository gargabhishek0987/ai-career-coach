import { Component } from '@angular/core';
import { CoachService } from '../../shared/services/coach.service';
@Component({
  selector: 'app-interview',
  templateUrl: './interview.component.html',
  styleUrls: ['./coach-shared.css']
})
export class InterviewComponent {
  jobRole  = '';
  loading  = false;
  error    = '';
  response = '';

  constructor(private coachService: CoachService) {}

  generate(): void {
    if (!this.jobRole.trim()) { this.error = 'Please enter a job role.'; return; }
    this.loading  = true;
    this.error    = '';
    this.response = '';

    this.coachService.generateInterviewQuestions(this.jobRole).subscribe({
      next: (res: any) => {
        this.loading  = false;
        this.response = res.aiResponse;
      },
      error: () => {
        this.loading = false;
        this.error   = 'Failed to generate questions. Please try again.';
      }
    });
  }
}

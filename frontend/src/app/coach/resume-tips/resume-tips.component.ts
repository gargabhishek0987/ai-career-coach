import { Component } from '@angular/core';
import { CoachService } from '../../shared/services/coach.service';

@Component({
  selector: 'app-resume-tips',
  templateUrl: './resume-tips.component.html',
  styleUrls: ['./coach-shared.css']
})
export class ResumeTipsComponent {
  loading  = false;
  error    = '';
  response = '';

  constructor(private coachService: CoachService) {}

  analyze(): void {
    this.loading  = true;
    this.error    = '';
    this.response = '';

    this.coachService.getResumeTips().subscribe({
      next: (res: any) => {
        this.loading  = false;
        if (res.status === 'error') { this.error = res.message; return; }
        this.response = res.aiResponse;
      },
      error: () => {
        this.loading = false;
        this.error   = 'Failed to analyze resume. Make sure you have uploaded your resume first.';
      }
    });
  }
}

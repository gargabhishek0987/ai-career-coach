import { Component } from '@angular/core';
import { CoachService } from '../../shared/services/coach.service';

@Component({
  selector: 'app-skill-gap',
  templateUrl: './skill-gap.component.html',
  styleUrls: ['./coach-shared.css']
})
export class SkillGapComponent {
  jobDescription = '';
  loading        = false;
  error          = '';
  response       = '';

  constructor(private coachService: CoachService) {}

  analyze(): void {
    if (!this.jobDescription.trim()) { this.error = 'Please paste a job description.'; return; }
    this.loading  = true;
    this.error    = '';
    this.response = '';

    this.coachService.analyzeSkillGap(this.jobDescription).subscribe({
      next: (res: any) => {
        this.loading  = false;
        if (res.status === 'error') { this.error = res.message; return; }
        this.response = res.aiResponse;
      },
      error: () => {
        this.loading = false;
        this.error   = 'Analysis failed. Make sure you have uploaded your resume first.';
      }
    });
  }
}

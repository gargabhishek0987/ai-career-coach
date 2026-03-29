import { Component } from '@angular/core';
import { CoachService } from '../../shared/services/coach.service';

@Component({
  selector: 'app-roadmap',
  templateUrl: './roadmap.component.html',
  styleUrls: ['./coach-shared.css']
})
export class RoadmapComponent {
  targetRole = '';
  loading    = false;
  error      = '';
  response   = '';

  constructor(private coachService: CoachService) {}

  generate(): void {
    if (!this.targetRole.trim()) { this.error = 'Please enter your target role.'; return; }
    this.loading  = true;
    this.error    = '';
    this.response = '';

    this.coachService.generateRoadmap(this.targetRole).subscribe({
      next: (res: any) => {
        this.loading  = false;
        this.response = res.aiResponse;
      },
      error: () => {
        this.loading = false;
        this.error   = 'Failed to generate roadmap. Please try again.';
      }
    });
  }
}

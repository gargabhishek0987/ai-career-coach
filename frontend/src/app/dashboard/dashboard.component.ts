import { Component, OnInit } from '@angular/core';
import { AuthService } from '../shared/services/auth.service';
import { CoachService } from '../shared/services/coach.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  stats: any = {};
  name = '';

  features = [
    { icon: '🎯', title: 'Interview Prep',   desc: 'Get AI-generated interview questions for any job role',          link: '/coach/interview',  color: '#4f46e5' },
    { icon: '📊', title: 'Skill Gap',        desc: 'Compare your resume against a job description',                  link: '/coach/skill-gap',  color: '#0891b2' },
    { icon: '💼', title: 'Job Suggestions',  desc: 'Discover roles that match your current skills',                  link: '/coach/job-roles',  color: '#059669' },
    { icon: '🗺️', title: 'Learning Roadmap', desc: 'Get a step-by-step plan to reach your target role',             link: '/coach/roadmap',    color: '#d97706' },
    { icon: '📝', title: 'Resume Tips',      desc: 'Get AI feedback and improvements for your resume',              link: '/coach/resume-tips', color: '#dc2626' },
    { icon: '📄', title: 'Upload Resume',    desc: 'Upload your PDF resume to unlock all AI features',               link: '/resume',            color: '#7c3aed' }
  ];

  constructor(public authService: AuthService, private coachService: CoachService) {}

  ngOnInit(): void {
    this.name = this.authService.getName() || 'there';
    this.coachService.getStats().subscribe({
      next: (res: any) => this.stats = res,
      error: () => {}
    });
  }
}

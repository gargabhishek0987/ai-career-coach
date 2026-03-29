import { Component, OnInit } from '@angular/core';
import { CoachService } from '../shared/services/coach.service';
@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {
  sessions: any[]  = [];
  loading          = false;
  expanded: number = -1;
  filter           = 'ALL';

  filters = [
    { label: 'All',          value: 'ALL'        },
    { label: 'Interview',    value: 'INTERVIEW'   },
    { label: 'Skill Gap',    value: 'SKILL_GAP'  },
    { label: 'Job Roles',    value: 'JOB_ROLES'  },
    { label: 'Roadmap',      value: 'ROADMAP'     },
    { label: 'Resume Tips',  value: 'RESUME_TIPS' }
  ];

  typeLabels: any = {
    INTERVIEW:   { label: 'Interview',   icon: '🎯', color: '#4f46e5' },
    SKILL_GAP:   { label: 'Skill Gap',   icon: '📊', color: '#0891b2' },
    JOB_ROLES:   { label: 'Job Roles',   icon: '💼', color: '#059669' },
    ROADMAP:     { label: 'Roadmap',     icon: '🗺️', color: '#d97706' },
    RESUME_TIPS: { label: 'Resume Tips', icon: '📝', color: '#dc2626' }
  };

  constructor(private coachService: CoachService) {}

  ngOnInit(): void {
    this.loading = true;
    this.coachService.getHistory().subscribe({
      next: (res: any[]) => { this.sessions = res; this.loading = false; },
      error: ()          => { this.loading = false; }
    });
  }

  get filtered(): any[] {
    if (this.filter === 'ALL') return this.sessions;
    return this.sessions.filter(s => s.sessionType === this.filter);
  }

  toggle(i: number): void {
    this.expanded = this.expanded === i ? -1 : i;
  }

  getInfo(type: string): any {
    return this.typeLabels[type] || { label: type, icon: '🤖', color: '#6b7280' };
  }
}

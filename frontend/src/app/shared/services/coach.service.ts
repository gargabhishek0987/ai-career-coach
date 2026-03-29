import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
@Injectable({ providedIn: 'root' })
export class CoachService {

  constructor(private http: HttpClient) {}

  generateInterviewQuestions(jobRole: string): Observable<any> {
    return this.http.post(`${environment.apiUrl}/coach/interview-questions`, { jobRole });
  }

  analyzeSkillGap(jobDescription: string): Observable<any> {
    return this.http.post(`${environment.apiUrl}/coach/skill-gap`, { jobDescription });
  }

  suggestJobRoles(): Observable<any> {
    return this.http.get(`${environment.apiUrl}/coach/job-suggestions`);
  }

  generateRoadmap(targetRole: string): Observable<any> {
    return this.http.post(`${environment.apiUrl}/coach/roadmap`, { targetRole });
  }

  getResumeTips(): Observable<any> {
    return this.http.get(`${environment.apiUrl}/coach/resume-tips`);
  }

  getHistory(): Observable<any> {
    return this.http.get(`${environment.apiUrl}/coach/history`);
  }

  getStats(): Observable<any> {
    return this.http.get(`${environment.apiUrl}/coach/stats`);
  }
}

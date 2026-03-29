import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './shared/guards/auth.guard';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ResumeComponent } from './resume/resume.component';
import { InterviewComponent } from './coach/interview/interview.component';
import { SkillGapComponent } from './coach/skill-gap/skill-gap.component';
import { JobRolesComponent } from './coach/job-roles/job-roles.component';
import { RoadmapComponent } from './coach/roadmap/roadmap.component';
import { ResumeTipsComponent } from './coach/resume-tips/resume-tips.component';
import { HistoryComponent } from './history/history.component';
import { OauthCallbackComponent } from './auth/oauth-callback/oauth-callback.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'oauth2/callback', component: OauthCallbackComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'resume', component: ResumeComponent, canActivate: [AuthGuard] },
  { path: 'coach/interview', component: InterviewComponent, canActivate: [AuthGuard] },
  { path: 'coach/skill-gap', component: SkillGapComponent, canActivate: [AuthGuard] },
  { path: 'coach/job-roles', component: JobRolesComponent, canActivate: [AuthGuard] },
  { path: 'coach/roadmap', component: RoadmapComponent, canActivate: [AuthGuard] },
  { path: 'coach/resume-tips', component: ResumeTipsComponent, canActivate: [AuthGuard] },
  { path: 'history', component: HistoryComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: '/dashboard' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}

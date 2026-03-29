import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { JwtInterceptor } from './shared/interceptors/jwt.interceptor';

import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { OauthCallbackComponent } from './auth/oauth-callback/oauth-callback.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ResumeComponent } from './resume/resume.component';
import { InterviewComponent } from './coach/interview/interview.component';
import { SkillGapComponent } from './coach/skill-gap/skill-gap.component';
import { JobRolesComponent } from './coach/job-roles/job-roles.component';
import { RoadmapComponent } from './coach/roadmap/roadmap.component';
import { ResumeTipsComponent } from './coach/resume-tips/resume-tips.component';
import { HistoryComponent } from './history/history.component';
import { NavbarComponent } from './shared/navbar/navbar.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    OauthCallbackComponent,
    DashboardComponent,
    ResumeComponent,
    InterviewComponent,
    SkillGapComponent,
    JobRolesComponent,
    RoadmapComponent,
    ResumeTipsComponent,
    HistoryComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}

# AI Career Coach Platform

> A full-stack AI-powered career coaching web application built with Spring Boot, Angular 15, OAuth 2.0, and Groq AI. Users log in with Google or GitHub, upload their resume, and get personalized AI coaching across 5 career features.

---

## Live Architecture

```
Angular 15 (Frontend)
        │
        ▼
Spring Boot REST API  ──►  Groq AI (Llama 3.1)
        │
        ├──► MySQL (users, resumes, sessions)
        │
        └──► OAuth 2.0 (Google + GitHub)
```

---

## Features

| Feature | Description |
|---|---|
| OAuth 2.0 Login | Sign in with Google or GitHub — no password needed |
| Email Registration | Classic email + password signup with JWT |
| Resume Upload | Upload PDF resume — text extracted automatically with Apache PDFBox |
| Interview Prep | Generate 10 interview questions with answer hints for any job role |
| Skill Gap Analyzer | AI compares your resume skills against a job description |
| Job Role Suggestions | AI suggests 8 matching roles based on your resume |
| Learning Roadmap | Step-by-step plan to reach any target job role |
| Resume Tips | AI reviews resume and gives specific improvement suggestions |
| Session History | All coaching sessions saved and filterable by type |
| JWT Security | Every API call protected with stateless Bearer token auth |

---

## Tech Stack

### Backend
| Layer | Technology |
|---|---|
| Framework | Spring Boot 2.7.18 |
| Language | Java 8 |
| Security | Spring Security + OAuth 2.0 + JWT |
| AI Integration | Groq API (Llama 3.1) |
| PDF Parsing | Apache PDFBox 2.0.29 |
| Database | MySQL + Spring Data JPA |
| Build | Maven |

### Frontend
| Layer | Technology |
|---|---|
| Framework | Angular 15 |
| Language | TypeScript |
| HTTP | Angular HttpClient + JWT Interceptor |
| Routing | Angular Router + Auth Guard |
| Styling | Custom CSS |

---

## Project Structure

```
ai-career-coach/
│
├── backend/                                        ← Spring Boot API
│   ├── pom.xml
│   └── src/main/java/com/aicoach/app/
│       ├── AiCareerCoachApplication.java           ← entry point
│       ├── config/
│       │   └── SecurityConfig.java                 ← OAuth2 + JWT + CORS
│       ├── controller/
│       │   ├── AuthController.java                 ← register, login, profile
│       │   ├── CoachController.java                ← 5 AI coaching endpoints
│       │   ├── ResumeController.java               ← PDF upload and fetch
│       │   └── HealthController.java
│       ├── model/
│       │   ├── User.java
│       │   ├── Resume.java
│       │   └── CoachingSession.java
│       ├── repository/
│       │   ├── UserRepository.java
│       │   ├── ResumeRepository.java
│       │   └── CoachingSessionRepository.java
│       ├── security/
│       │   ├── JwtUtil.java                        ← token generation & validation
│       │   ├── JwtFilter.java                      ← intercepts every request
│       │   ├── OAuth2SuccessHandler.java           ← handles Google/GitHub callback
│       │   └── CustomUserDetailsService.java
│       └── service/
│           ├── AuthService.java
│           ├── CoachService.java                   ← all 5 AI features
│           ├── ResumeService.java                  ← PDF text extraction
│           └── GroqAIService.java                  ← Groq API integration
│
└── frontend/                                       ← Angular 15 SPA
    └── src/app/
        ├── auth/
        │   ├── login/                              ← email + Google + GitHub login
        │   ├── register/                           ← signup page
        │   └── oauth-callback/                     ← OAuth redirect handler
        ├── dashboard/                              ← stats + feature cards
        ├── resume/                                 ← drag & drop PDF upload
        ├── coach/
        │   ├── interview/                          ← interview question generator
        │   ├── skill-gap/                          ← skill gap analyzer
        │   ├── job-roles/                          ← job role suggestions
        │   ├── roadmap/                            ← learning roadmap
        │   └── resume-tips/                        ← resume improvement tips
        ├── history/                                ← session history with filters
        └── shared/
            ├── services/                           ← auth, coach, resume services
            ├── interceptors/jwt.interceptor.ts     ← auto-attaches Bearer token
            ├── guards/auth.guard.ts                ← protects private routes
            └── navbar/                             ← top navigation
```

---

## Prerequisites

- Java 8
- Maven
- MySQL
- Node.js 16+
- Angular CLI 15: `npm install -g @angular/cli@15`
- Free Groq API key from [console.groq.com](https://console.groq.com)
- Google OAuth credentials from [console.cloud.google.com](https://console.cloud.google.com)
- GitHub OAuth credentials from [github.com/settings/developers](https://github.com/settings/developers)

---

## Setup

### Step 1 — Create MySQL Database

```sql
CREATE DATABASE ai_career_coach_db;
```

### Step 2 — Configure Backend

Open `backend/src/main/resources/application.properties` and fill in your values:

```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD

spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET

spring.security.oauth2.client.registration.github.client-id=YOUR_GITHUB_CLIENT_ID
spring.security.oauth2.client.registration.github.client-secret=YOUR_GITHUB_CLIENT_SECRET

groq.api.key=YOUR_GROQ_API_KEY
```

### Step 3 — Run Backend

```bash
cd backend
mvn spring-boot:run
```

Backend starts at: `http://localhost:8080`

### Step 4 — Run Frontend

```bash
cd frontend
npm install
ng serve
```

Frontend starts at: `http://localhost:4200`

---

## OAuth 2.0 Setup

### Google OAuth

1. Go to [console.cloud.google.com](https://console.cloud.google.com)
2. Create project → APIs & Services → Credentials → OAuth Client ID
3. Application type: Web application
4. Add redirect URI: `http://localhost:8080/login/oauth2/code/google`
5. Copy Client ID and Client Secret to `application.properties`

### GitHub OAuth

1. Go to [github.com/settings/developers](https://github.com/settings/developers)
2. New OAuth App
3. Authorization callback URL: `http://localhost:8080/login/oauth2/code/github`
4. Copy Client ID and Client Secret to `application.properties`

---

## API Reference

### Auth Endpoints (Public)
```
POST  /api/auth/register    body: { email, password, name }
POST  /api/auth/login       body: { email, password }
GET   /api/health
```

### Protected Endpoints (Require Bearer Token)
```
GET   /api/auth/profile
POST  /api/resume/upload              multipart/form-data: file
GET   /api/resume/my-resume
POST  /api/coach/interview-questions  body: { jobRole }
POST  /api/coach/skill-gap            body: { jobDescription }
GET   /api/coach/job-suggestions
POST  /api/coach/roadmap              body: { targetRole }
GET   /api/coach/resume-tips
GET   /api/coach/history
GET   /api/coach/stats
```

---

## How OAuth 2.0 Flow Works

```
1. User clicks "Login with Google"
2. Spring Boot redirects to Google's auth server
3. User logs in on Google's page
4. Google redirects to /login/oauth2/code/google
5. OAuth2SuccessHandler extracts email and name
6. If user does not exist → auto-creates account in MySQL
7. Generates JWT token
8. Redirects to Angular at /oauth2/callback?token=...
9. Angular stores token and navigates to dashboard
```

---

## Database Schema

Tables are auto-created by Hibernate on first run.

**users** — id, email, password (BCrypt), name, profile_picture, provider (LOCAL/GOOGLE/GITHUB), role, created_at

**resumes** — id, user_email, file_name, extracted_text, uploaded_at

**coaching_sessions** — id, user_email, session_type, user_input, ai_response, created_at

---

## Common Errors

| Error | Fix |
|---|---|
| `npm install` fails | Run `npm install --legacy-peer-deps` |
| `401 Unauthorized` on API calls | Token expired — login again and use new token |
| OAuth redirect fails | Check redirect URI in Google/GitHub console matches exactly |
| PDF parse returns empty | PDF must be text-based, not a scanned image |
| CORS error in browser | Check `SecurityConfig.java` allows `http://localhost:4200` |
| `model_decommissioned` in AI response | Change `groq.model` to `llama-3.1-8b-instant` in properties |
| Port 8080 already in use | Add `server.port=8081` in `application.properties` |

---

## Future Scope

- **RabbitMQ** — async email notifications after each coaching session
- **Microservices** — split into Auth, Resume, Coach, Notification services
- **Docker Compose** — one command to run the entire stack
- **Cover Letter Generator** — AI writes a cover letter from resume + job description
- **Mock Interview Chat** — conversational interview practice with AI feedback
- **LinkedIn Summary Generator** — AI writes your LinkedIn bio from resume

---

## Skills Demonstrated

This project covers the core skills for a System Integrator role:

- External API integration (Groq AI REST API)
- OAuth 2.0 authorization flow (Google + GitHub)
- JWT stateless authentication
- PDF file parsing and text extraction
- Full-stack integration (Angular ↔ Spring Boot)
- Database design with JPA/Hibernate
- CORS configuration for cross-origin requests
- Role-based access control foundation
- Angular route guards and HTTP interceptors
- Cloud-ready, microservices-ready architecture
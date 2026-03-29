package com.aicoach.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiCareerCoachApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiCareerCoachApplication.class, args);
        System.out.println("==============================================");
        System.out.println("  AI Career Coach API is running!");
        System.out.println("  Backend URL : http://localhost:8080");
        System.out.println("  Frontend URL: http://localhost:4200");
        System.out.println("==============================================");
    }
}

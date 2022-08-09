package com.example.spring_3th_assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class Spring3thAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring3thAssignmentApplication.class, args);
    }

}

package com.binarfud.challenge7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Challenge7Application {

    public static void main(String[] args) {
        SpringApplication.run(Challenge7Application.class, args);
    }

}

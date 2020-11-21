package com.sylvan.hecate.knowledge;

import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
public class Bootstrap {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Bootstrap.class);
        System.out.println(Bootstrap.class.getName());
        springApplication.run(args);
    }
}

package com.sylvan.auro.persistence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
public class TestBoostrap {
  public static void main(String[] args) {
    SpringApplication.run(TestBoostrap.class, args);
  }
}

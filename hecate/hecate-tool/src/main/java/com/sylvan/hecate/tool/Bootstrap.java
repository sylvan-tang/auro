package com.sylvan.hecate.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/** @author sylvan */
@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
@ComponentScan(basePackages = {"com.sylvan.hecate.tool", "com.sylvan.hecate.persistence"})
public class Bootstrap {
  public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(Bootstrap.class);
    System.out.println(Bootstrap.class.getName());
    springApplication.run(args);
  }
}

package com.sylvan.hecate.persistence.config;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JooqConfig {

  @Bean
  public HikariDataSource dataSource(DataSourceProperties dataSourceProperties) {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(dataSourceProperties.getUrl());
    dataSource.setUsername(dataSourceProperties.getUsername());
    dataSource.setPassword(dataSourceProperties.getPassword());
    return dataSource;
  }

  @Bean
  public org.jooq.Configuration configuration(HikariDataSource dataSource) {
    DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
    jooqConfiguration.setDataSource(dataSource);
    jooqConfiguration.set(new DefaultExecuteListenerProvider(new DefaultExecuteListener()));
    jooqConfiguration.setSQLDialect(SQLDialect.MYSQL_5_7);
    return jooqConfiguration;
  }

  @Bean
  public DSLContext context(org.jooq.Configuration configuration) {
    return new DefaultDSLContext(configuration);
  }
}

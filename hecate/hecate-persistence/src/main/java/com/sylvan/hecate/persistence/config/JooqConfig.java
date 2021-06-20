package com.sylvan.hecate.persistence.config;

import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** 提供数据库上下文 */
@Configuration
public class JooqConfig {

  /** 使用 spring.database 默认配置，生成 hikariDataSource 连接池 */
  @Bean
  public HikariDataSource dataSource(DataSourceProperties dataSourceProperties) {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(dataSourceProperties.getUrl());
    dataSource.setUsername(dataSourceProperties.getUsername());
    dataSource.setPassword(dataSourceProperties.getPassword());
    return dataSource;
  }

  /** 使用 HikariDataSource bean，生成 jooq 配置 bean */
  @Bean
  public org.jooq.Configuration configuration(HikariDataSource dataSource) {
    DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
    jooqConfiguration.setDataSource(dataSource);
    jooqConfiguration.set(new DefaultExecuteListenerProvider(new DefaultExecuteListener()));
    jooqConfiguration.setSQLDialect(SQLDialect.MYSQL_5_7);
    return jooqConfiguration;
  }

  /** 使用 jooq bean 生成全局的 DSLContext，用于注入到 dao 对象中 */
  @Bean
  public DSLContext context(org.jooq.Configuration configuration) {
    return new DefaultDSLContext(configuration);
  }
}

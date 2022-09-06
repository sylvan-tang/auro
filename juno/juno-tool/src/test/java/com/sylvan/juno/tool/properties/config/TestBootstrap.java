package com.sylvan.juno.tool.properties.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author sylvan
 * @date 2020/8/7
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TestBootstrap {}

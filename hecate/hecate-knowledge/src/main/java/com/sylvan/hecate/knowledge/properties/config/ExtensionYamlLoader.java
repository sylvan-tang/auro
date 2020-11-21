package com.sylvan.hecate.knowledge.properties.config;

import java.io.IOException;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 使用 ExtensionYamlLoader 可以在 application 启动前将指定目录下的 yml 文件中的配置读到 ConfigurableEnvironment 中或 System
 * 配置中 使用方法： 1. 在 resources 中新增目录，将需要加载的变量添加到对应目录中的 yml 文件中 2. 在 resources/META-INF/spring.factories
 * 中配置
 * org.springframework.context.ApplicationListener=com.sylvan.hecate.knowledge.properties.config.ExtensionYamlLoader
 * 3. 在启动类中加上 @SpringBootApplication
 *
 * @author sylvan
 * @date 2020/8/7
 */
public class ExtensionYamlLoader
    implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  /** 存放需要加载到 ConfigurableEnvironment 中的 yml 文件 */
  private final String environmentPropertySources;

  /** 存放需要加载到 System 配置中的 yml 文件 */
  private final String systemPropertySources;

  public ExtensionYamlLoader() {
    this.environmentPropertySources = "environment-property-sources";
    this.systemPropertySources = "system-property-sources";
  }

  public ExtensionYamlLoader(String environmentPropertySources, String systemPropertySources) {
    this.environmentPropertySources = environmentPropertySources;
    this.systemPropertySources = systemPropertySources;
  }

  @Override
  public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
    ConfigurableEnvironment environment = event.getEnvironment();

    try {
      System.out.println(String.format("Loading yaml from folder: %s", environmentPropertySources));
      YamlLoaderUtils.loadYamlByPattern(environmentPropertySources)
          .forEach(propertySource -> environment.getPropertySources().addLast(propertySource));

      YamlLoaderUtils.loadYmlToSystem(systemPropertySources);
      System.out.println("End of ExtensionYamlLoader.");
    } catch (IOException e) {
      System.out.println(
          String.format(
              "Failed to load additional yaml file with pattern %s:", environmentPropertySources));
      e.printStackTrace();
      System.exit(1);
    }
  }
}

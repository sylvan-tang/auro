package com.sylvan.jasper.knowledge.properties.config;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

/**
 * @author sylvan
 * @date 2020/8/7
 */
public class YamlLoaderUtils {

  public static String pathPattern = "classpath*:%s/*.yml";

  /** 只在系统没有相同参数时才设置对应的系统参数 */
  public static Map<String, String> loadYmlToSystem(String sourcesFolder) {
    try {
      Map<String, String> properties = new HashMap<>();
      loadYamlByPattern(sourcesFolder)
          .forEach(
              propertySource -> {
                System.out.println("System: " + propertySource.getName());
                Map<String, Object> source = (Map<String, Object>) propertySource.getSource();
                source.forEach(
                    (propertyName, defaultPropertyValue) -> {
                      if (StringUtils.isEmpty(System.getProperty(propertyName))) {
                        System.out.println(
                            String.format(
                                "%s from system property is blank, set to %s",
                                propertyName, defaultPropertyValue));
                        System.setProperty(propertyName, defaultPropertyValue.toString());
                        properties.put(propertyName, defaultPropertyValue.toString());
                      }
                    });
              });
      return properties;
    } catch (IOException e) {
      System.out.println(
          String.format("Failed to load additional yaml file with pattern %s:", sourcesFolder));
      e.printStackTrace();
    }
    return Collections.emptyMap();
  }

  public static List<PropertySource<?>> loadYamlByPattern(String sourcesFolder) throws IOException {
    if (Objects.isNull(sourcesFolder)) {
      System.out.println("Input param require not null.");
      return Collections.emptyList();
    }
    String pattern = String.format(pathPattern, sourcesFolder);
    Resource[] resources = new PathMatchingResourcePatternResolver().getResources(pattern);
    System.out.println(
        String.format("Get %s resources with pattern %s.", resources.length, pattern));
    YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
    return Arrays.stream(resources)
        .filter(Resource::exists)
        .map(resource -> loadYaml(loader, "resource-" + resource.getFilename(), resource))
        .collect(Collectors.toList());
  }

  private static PropertySource<?> loadYaml(
      YamlPropertySourceLoader loader, String name, Resource path) {
    if (!path.exists()) {
      throw new IllegalArgumentException("Resource " + path + " does not exist");
    }
    try {
      PropertySource<?> propertySource = loader.load(name, path).get(0);
      System.out.println(String.format("Load resource from: %s", path));
      return propertySource;
    } catch (IOException ex) {
      throw new IllegalStateException("Failed to load yaml configuration from " + path, ex);
    }
  }
}

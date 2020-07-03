package com.dovit.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * @author Ramón París
 * @since 19-06-20
 */
@Configuration
public class EmailConfig {

  @Bean
  public FreeMarkerViewResolver freemarkerViewResolver() {
    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
    resolver.setSuffix(".ftl");
    return resolver;
  }

  @Bean
  public FreeMarkerConfigurer freemarkerConfig() {
    FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
    freeMarkerConfigurer.setTemplateLoaderPath("classpath:/mails/");
    return freeMarkerConfigurer;
  }
}

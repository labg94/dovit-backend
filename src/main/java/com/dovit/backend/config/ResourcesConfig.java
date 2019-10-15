package com.dovit.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Ramón París
 * @since 15-10-2019
 */
@Configuration
public class ResourcesConfig extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry ){
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}

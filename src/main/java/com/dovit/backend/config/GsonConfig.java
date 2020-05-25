package com.dovit.backend.config;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * @author Ramón París
 * @since 24-05-20
 */
@Configuration
public class GsonConfig {

  @Bean
  public Gson gson() {
    return new GsonBuilder()
        .addSerializationExclusionStrategy(
            new ExclusionStrategy() {
              @Override
              public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getAnnotation(ManyToOne.class) != null
                    || fieldAttributes.getAnnotation(OneToMany.class) != null
                    || fieldAttributes.getAnnotation(ManyToMany.class) != null
                    || fieldAttributes.getAnnotation(OneToOne.class) != null;
              }

              @Override
              public boolean shouldSkipClass(Class<?> aClass) {
                return false;
              }
            })
        .create();
  }
}

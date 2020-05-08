package com.dovit.backend.config;

import com.dovit.backend.domain.DevOpsCategory;
import com.dovit.backend.domain.DevOpsSubcategory;
import com.dovit.backend.domain.Tool;
import com.dovit.backend.model.responses.ToolResponse;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Ramón París
 * @since 29-03-20
 */
@Configuration
public class ModelMapperConfig {

  @Value("${api.image.route}")
  private String BASE_IMAGE_URL;

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setDeepCopyEnabled(false).setSkipNullEnabled(true);
    modelMapper.addMappings(this.toolResponsePropertyMap(BASE_IMAGE_URL));
    return modelMapper;
  }

  /** Property Map used to map DevOpsSubcategory and DevOpsCategory into tags */
  private PropertyMap<Tool, ToolResponse> toolResponsePropertyMap(String BASE_IMAGE_URL) {
    Converter<List<DevOpsSubcategory>, List<String>> converterTags =
        mappingContext -> {
          List<String> tags =
              mappingContext.getSource().stream()
                  .map(DevOpsSubcategory::getDescription)
                  .collect(Collectors.toList());

          List<String> parentTags =
              mappingContext.getSource().stream()
                  .map(DevOpsSubcategory::getDevOpsCategory)
                  .map(DevOpsCategory::getDescription)
                  .distinct()
                  .collect(Collectors.toList());

          return Stream.concat(parentTags.stream(), tags.stream()).collect(Collectors.toList());
        };

    Converter<String, String> convertImgUrl =
        mappingContext -> BASE_IMAGE_URL + mappingContext.getSource();

    return new PropertyMap<>() {
      @Override
      protected void configure() {
        using(converterTags).map(source.getSubcategories()).setTags(new ArrayList<>());
        using(convertImgUrl).map(source.getImageUrl()).setImageUrl(BASE_IMAGE_URL);
      }
    };
  }
}

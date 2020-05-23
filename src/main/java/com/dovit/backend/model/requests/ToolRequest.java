package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Ramón París
 * @since 23-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToolRequest {

  private Long id;

  @NotEmpty(message = "Name is a mandatory field")
  private String name;

  @NotEmpty(message = "Description is a mandatory field")
  private String description;

  // todo image

  @NotNull(message = "Subcategory is a mandatory field")
  private List<Long> subcategoryIds;

  private List<LicenseRequest> licenses;
}

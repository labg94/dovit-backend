package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ToolResponse {

  private Long toolId;
  private String toolName;
  private String imageUrl;
  private String description;
  private boolean active;
  private List<String> tags;
}

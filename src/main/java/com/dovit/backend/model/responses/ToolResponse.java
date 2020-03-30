package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
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
public class ToolResponse {

  private Long toolId;
  private String toolName;
  private String urlImg;
  private List<String> tags;
}

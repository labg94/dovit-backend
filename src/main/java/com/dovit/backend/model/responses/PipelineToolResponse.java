package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 31-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PipelineToolResponse {

  private Long categoryId;
  private String categoryDescription;
  private List<ToolResponse> tools;
  private List<MemberResponseDetail> members;
}

package com.dovit.backend.model.responses;

import com.dovit.backend.domain.ToolProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 08-12-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToolProfileResponse {

  private Long toolId;
  private String toolName;
  private String imageUrl;
  private Long levelId;
  private String levelDesc;

  public ToolProfileResponse(ToolProfile toolProfile) {
    this.toolId = toolProfile.getToolId();
    this.toolName = toolProfile.getTool().getName();
    this.imageUrl = toolProfile.getTool().getImageUrl();
    this.levelId = toolProfile.getLevelId();
    this.levelDesc = toolProfile.getLevel().getDescription();
  }
}

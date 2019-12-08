package com.dovit.backend.model.responses;

import com.dovit.backend.domain.ToolProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 08-12-2019
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolProfileResponse extends ToolResponse{

    private Long levelId;
    private String levelDesc;

    public ToolProfileResponse(ToolProfile toolProfile) {
        this.setToolId(toolProfile.getToolId());
        this.setToolName(toolProfile.getTool().getName());
        this.setUrlImg(toolProfile.getTool().getImageUrl());
        this.levelId = toolProfile.getLevelId();
        this.levelDesc = toolProfile.getLevel().getDescription();
    }
}

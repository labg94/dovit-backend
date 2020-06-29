package com.dovit.backend.model;

import com.dovit.backend.domain.DevOpsCategory;
import com.dovit.backend.domain.ToolProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 28-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberKnowledgeHelperDTO {

    private ToolProfile toolProfile;
    private DevOpsCategory devOpsCategory;
}

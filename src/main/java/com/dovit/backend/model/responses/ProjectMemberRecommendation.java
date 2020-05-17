package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 10-12-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberRecommendation {

    private Long devOpsCategoryId;
    private String devOpsCategoryDescription;
    private List<MemberResponse> membersRecommendation;
}

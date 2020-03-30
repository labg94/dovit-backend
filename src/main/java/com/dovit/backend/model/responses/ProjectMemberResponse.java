package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMemberResponse {

    private Long memberId;
    private String memberName;
    private String memberLastName;
    private List<MemberParticipationResponse> participation;
}

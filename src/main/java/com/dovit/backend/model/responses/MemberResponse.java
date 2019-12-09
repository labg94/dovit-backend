package com.dovit.backend.model.responses;

import com.dovit.backend.domain.Member;
import com.dovit.backend.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 08-12-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

    private Long id;

    private Long companyId;
    private String companyName;

    private String memberName;

    private String memberLastName;

    private Boolean active;

    private List<ProfileResponse> profiles;

    private List<ToolProfileResponse> tools;

    public MemberResponse(Member member) {

        this.id = member.getId();
        this.companyId = member.getCompany().getId();
        this.companyName = member.getCompany().getName();
        this.memberName = member.getName();
        this.memberLastName = member.getLastName();
        this.active = member.getActive();
        this.profiles = member.getProfiles().stream().map(ProfileResponse::new).collect(Collectors.toList());
        this.tools = member.getToolProfile().stream().map(ToolProfileResponse::new).collect(Collectors.toList());

    }
}

package com.dovit.backend.model.requests;

import com.dovit.backend.domain.Company;
import com.dovit.backend.domain.ProjectMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequest {

    private Long id;
    private String name;
    private Date start;
    private String observation;
    private Long companyId;
    private Boolean finished;
    private List<ProjectMemberRequest> members;

}

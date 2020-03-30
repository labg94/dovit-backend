package com.dovit.backend.model.responses;

import com.dovit.backend.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {

    private Long id;
    private String name;
    private Date start;
    private String observation;
    private Long companyId;
    private String companyName;
    private Boolean finished;
    private List<ProjectMemberResponse> members;

    public ProjectResponse(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.start = Date.from(project.getStart());
        this.observation = project.getObservation();
        this.companyId = project.getCompany().getId();
        this.companyName = project.getCompany().getName();
        this.finished = project.getFinished();
    }
}

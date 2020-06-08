package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * @author Ramón París
 * @since 31-05-20
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectType extends DateAudit {

  @Id @GeneratedValue private Long id;

  @NotNull private String description;

  private String observation;

  @ManyToMany(mappedBy = "projectTypes")
  private List<Project> project;

  @OneToMany(mappedBy = "projectType")
  private Collection<ToolProjectType> tools;

  public Collection<ToolProjectType> getTools() {
    return tools;
  }

  public void setTools(Collection<ToolProjectType> tools) {
    this.tools = tools;
  }
}

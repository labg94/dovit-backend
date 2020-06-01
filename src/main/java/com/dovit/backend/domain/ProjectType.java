package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
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

  @ManyToMany(mappedBy = "projectTypes")
  private List<Tool> tools;
}

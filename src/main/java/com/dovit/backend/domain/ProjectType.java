package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
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

  private String description;

  private String observation;

  @ManyToMany
  @JoinTable(
      name = "project_project_types",
      joinColumns = @JoinColumn(name = "project_type_id"),
      inverseJoinColumns = @JoinColumn(name = "project_id"))
  private List<Project> project;
}

package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank private String name;

  @Column private LocalDate start;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column private String observation;

  @Column private Boolean finished;

  @ManyToOne private Company company;

  @OneToMany(mappedBy = "project")
  private List<ProjectMember> members;

  @ManyToMany
  @JoinTable(
      name = "project_project_types",
      joinColumns = @JoinColumn(name = "project_id"),
      inverseJoinColumns = @JoinColumn(name = "project_type_id"))
  private List<ProjectType> projectTypes;

  @OneToMany(mappedBy = "project")
  private List<Pipeline> pipelines;
}

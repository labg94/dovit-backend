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
@Table(
    name = "project",
    uniqueConstraints = @UniqueConstraint(columnNames = {"name", "company_id"}))
public class Project extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank
  @Column(name = "name", nullable = false)
  private String name;

  @Column private LocalDate start;

  @Column(name = "end_date")
  private LocalDate endDate;

  @Column private String observation;

  @Column private Boolean finished;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;

  @OneToMany(mappedBy = "project", cascade = CascadeType.REFRESH)
  private List<ProjectMember> members;

  @ManyToMany(cascade = CascadeType.REFRESH)
  @JoinTable(
      name = "project_project_types",
      joinColumns = @JoinColumn(name = "project_id"),
      inverseJoinColumns = @JoinColumn(name = "project_type_id"))
  private List<ProjectType> projectTypes;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
  private List<Pipeline> pipelines;
}

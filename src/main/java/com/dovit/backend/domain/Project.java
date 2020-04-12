package com.dovit.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotBlank private String name;

  private LocalDateTime start;

  private String observation;

  private Boolean finished;

  @ManyToOne private Company company;

  @OneToMany(mappedBy = "project")
  private List<ProjectMember> members;
}

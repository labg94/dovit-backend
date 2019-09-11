package com.dovit.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private LocalDateTime initialDate;
  private LocalDateTime estimatedFinalDate;

  private String clientName;

  private Integer totalCost;
  private Integer budget;


  @OneToMany(mappedBy = "project")
  private List<ProjectResource> projectResources;

  @ManyToOne
  private Company company;
}

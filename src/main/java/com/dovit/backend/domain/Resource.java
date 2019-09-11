package com.dovit.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resource {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne private Company company;

  @OneToMany(mappedBy = "resource")
  private List<ProfileExpertise> profileExpertise;

  @OneToMany(mappedBy = "resource")
  private List<ProjectResource> projectResources;

  @OneToMany(mappedBy = "resource")
  private List<ResourceToolExpertise> resourceToolExpertises;
}

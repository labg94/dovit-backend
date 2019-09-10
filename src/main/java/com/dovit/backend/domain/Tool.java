package com.dovit.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tool {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @OneToMany(mappedBy = "tool")
  private List<TechnologyCategory> technologyCategory;

  @OneToMany(mappedBy = "tool")
  private List<PayCategory> payCategories;

  @OneToMany(mappedBy = "tool")
  private List<AreaCategory> areaCategory;

  @OneToMany(mappedBy = "tool")
  private List<License> license;

  @OneToMany(mappedBy = "tool")
  private List<ResourceToolExpertise> resourceToolExpertises;

  @OneToMany(mappedBy = "tool")
  private List<ProjectResource> projectResources;
}

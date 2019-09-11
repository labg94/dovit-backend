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
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  private String location;

  @OneToMany(mappedBy = "company")
  private List<License> license;

  @OneToMany(mappedBy = "company")
  private List<Resource> resources;

  @OneToMany(mappedBy = "company")
  private List<CompanySubscription> companySubscriptions;

  @OneToMany(mappedBy = "company")
  private List<Project> projects;
}

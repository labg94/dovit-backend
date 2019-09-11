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
public class Profile {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String description;


  @OneToMany(mappedBy = "profile")
  private List<ProfileExpertise> profileExpertises;
}

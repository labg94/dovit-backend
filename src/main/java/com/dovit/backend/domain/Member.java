package com.dovit.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
    name = "members")
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne private Company company;

  @NotBlank private String name;

  @NotBlank private String lastName;

  @ManyToMany
  @JoinTable(name = "member_profiles",
          joinColumns = @JoinColumn(name = "member_id"),
          inverseJoinColumns =  @JoinColumn(name = "profile_id")
  )
  private List<Profile> profiles;

  @OneToMany(mappedBy = "member")
  private List<ToolProfile> toolProfile;



}

package com.dovit.backend.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Profile {

  @Id private Long id;

  @NotBlank private String description;

  @ManyToMany(mappedBy = "profiles")
  private List<Member> members;
}

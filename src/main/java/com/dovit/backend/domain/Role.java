package com.dovit.backend.domain;

import com.dovit.backend.util.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * User's roles.
 *
 * @author Ramón París
 * @since 01-10-2019
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@Builder
public class Role {

  @Id private Long id;

  @Enumerated(EnumType.STRING)
  private RoleName name;

  private String description;

  @OneToMany(mappedBy = "role")
  private List<User> users;
}

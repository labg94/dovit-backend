package com.dovit.backend.domain;

import javax.persistence.*;
import java.util.List;

/**
 * @author Ramón París
 * @since 10-09-2019
 */
@Entity
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "role_id")
  private Long id;

  private String description;

  @ManyToMany
  private List<User> users;
}

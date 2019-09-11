package com.dovit.backend.domain;

import javax.persistence.*;
import java.util.List;

/**
 * @author Ramón París
 * @since 10-09-2019
 */
@Entity
public class User {

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> roles;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    private String name;
}

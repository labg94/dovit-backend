package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * Users of the solution
 *
 * @author Ramón París
 * @since 01-10-2019
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(
    name = "users",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@AllArgsConstructor
@NoArgsConstructor
public class User extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank private String name;

  @NotBlank private String lastName;

  @NotBlank @Email private String email;

  @NotBlank private String password;

  @ManyToOne private Role role;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;

  private boolean active;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Audit> auditList;

  public User(
      @NotBlank String name,
      @NotBlank String lastName,
      @NotBlank @Email String email,
      boolean active) {
    this.name = name;
    this.lastName = lastName;
    this.email = email;
    this.active = active;
  }
}

package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
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
    uniqueConstraints = {
      @UniqueConstraint(columnNames = {"email"}),
      @UniqueConstraint(columnNames = {"rut"})
    })
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank private String name;

  @NotBlank private String lastName;

  @NotBlank @Email private String email;

  @NotBlank private String password;

  @NotBlank private String rut;

  @ManyToOne private Role role;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;

  @Column(columnDefinition = "boolean default true")
  private boolean active;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Audit> auditList;

  @OneToMany(mappedBy = "suggestedBy")
  private Collection<SuggestionMailbox> suggestionMailbox;
}

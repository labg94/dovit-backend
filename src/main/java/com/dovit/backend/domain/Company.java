package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

/**
 * Entity class that refers "client's company" table.
 *
 * @author Ramón París
 * @since 29-09-2019
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "companies")
@Builder
public class Company extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "company_id")
  private Long id;

  @NotEmpty private String name;

  @Column(columnDefinition = "boolean default true")
  private boolean active;

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<CompanyLicense> licenses;

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<User> users;

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Member> members;

  @OneToMany(mappedBy = "company")
  private Collection<Project> projects;

  @ManyToOne private Holding holding;
}

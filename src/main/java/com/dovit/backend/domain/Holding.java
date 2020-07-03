package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author Ramón París
 * @since 23-06-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "holding")
@Builder
public class Holding extends DateAudit {

  @Id
  @GeneratedValue
  @Column(name = "holder_id")
  private Long id;

  @NotEmpty @Column private String name;

  @OneToMany(mappedBy = "holding")
  private List<Company> companies;
}

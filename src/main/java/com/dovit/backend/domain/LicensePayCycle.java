package com.dovit.backend.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author Ramón París
 * @since 23-05-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Builder
@ToString
public class LicensePayCycle {

  @Id private Long id;

  @Column private String description;

  @OneToMany(mappedBy = "payCycle")
  private List<License> licenses;
}

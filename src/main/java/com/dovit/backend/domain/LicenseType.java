package com.dovit.backend.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Master table/entity that stores the type of licenses. It can be: hosted, free or paid
 *
 * @author Ramón París
 * @since 01-10-2019
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LicenseType {

  @Id private Long id;

  @NotEmpty private String description;

  @OneToMany(mappedBy = "licenseType")
  private List<License> licenses;
}

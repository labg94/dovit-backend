package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Licenses that a tool gives. This entity stores details of pricing and capacity
 *
 * @author Ramón París
 * @since 01-10-2019
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "licenses")
@ToString
public class License extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "license_id")
  private Long id;

  @NotEmpty private String name;

  @NotEmpty private String payCycle;

  private String observation;

  @ManyToOne
  @JoinColumn(nullable = false)
  private LicenseType licenseType;

  @OneToMany(mappedBy = "license")
  private List<CompanyLicense> companyLicenses;

  @ManyToOne
  @JoinColumn(name = "tool_id", nullable = false)
  private Tool tool;

  @OneToMany(mappedBy = "license")
  private List<LicensePricing> licensePrices;
}

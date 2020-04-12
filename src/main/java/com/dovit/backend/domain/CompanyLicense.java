package com.dovit.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Licenses that a company have with their initial and expiration date
 *
 * @author Ramón París
 * @since 29-09-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company_licenses")
@Builder
public class CompanyLicense {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "company_license_id")
  private Long id;

  @Column(nullable = false)
  private LocalDate startDate;

  private LocalDate expirationDate;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;

  @ManyToOne
  @JoinColumn(name = "license_id")
  private License license;
}

package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "licenses_pricing")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LicensePricing extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "license_pricing_sequence")
  @SequenceGenerator(initialValue = 100, name = "license_pricing_sequence")
  @Column(name = "license_pricing_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "license_id", nullable = false)
  private License license;

  private Long minUsers;

  private Long maxUsers;

  private Double price;
}

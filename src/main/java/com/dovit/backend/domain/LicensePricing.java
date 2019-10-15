package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;

import javax.persistence.*;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
@Entity
@Table(name = "licensesPricing")
public class LicensePricing extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "license_pricing_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "license_id")
    private License license;

    private Long minUsers;

    private Long maxUsers;

    private Double price;



}

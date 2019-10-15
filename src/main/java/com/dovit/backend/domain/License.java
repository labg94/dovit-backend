package com.dovit.backend.domain;

import com.dovit.backend.domain.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Licenses that a tool gives. This entity stores details of pricing and capacity
 *
 * @author Ramón París
 * @since 01-10-2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "licenses")
public class License extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "license_id")
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String payCycle;

    private String observation;

    @ManyToOne
    @JoinColumn(nullable = false)
    private LicenseType licenseType;

    @OneToMany(mappedBy = "license")
    private List<CompanyLicense> companyLicenses;

    @ManyToOne
    @JoinColumn(name="tool_id", nullable = false)
    private Tool tool;

    @OneToMany(mappedBy = "license")
    private List<LicensePricing> pricings;

}

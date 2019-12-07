package com.dovit.backend.model.responses;

import com.dovit.backend.model.LicenseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @author Ramón París
 * @since 04-10-2019
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyLicensesResponse extends LicenseDTO {

    private Long companyLicenseId;
    private Instant start;
    private Instant expiration;

}

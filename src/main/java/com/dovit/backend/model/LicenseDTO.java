package com.dovit.backend.model;

import com.dovit.backend.model.responses.LicensePricingResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LicenseDTO {

    private Long licenseId;
    private String licenseDesc;
    private String observation;
    private String payCycle;
    private Long licenseTypeId;
    private String licenseTypeDesc;
    private List<LicensePricingResponse> licensePricing;
}

package com.dovit.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}

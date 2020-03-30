package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Ramón París
 * @since 15-10-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyLicenseRequest {

    private Long id;

    @NotNull(message = "Debe seleccionar una empresa")
    private Long companyId;

    @NotNull(message = "Debe seleccionar una licencia")
    private Long licenseId;

    @NotNull(message = "Seleccione una fecha de inicio")
    private Date startDate;

    private Date expirationDate;
}

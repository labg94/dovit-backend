package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 15-10-2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LicensePricingResponse {

    private Long licensePricingId;
    private Long minUsers;
    private Long maxUsers;
    private Double price;
}

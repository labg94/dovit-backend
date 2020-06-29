package com.dovit.backend.model;

import com.dovit.backend.domain.CompanyLicense;
import com.dovit.backend.domain.DevOpsCategory;
import lombok.Builder;
import lombok.Data;

/**
 * @author Ramón París
 * @since 29-06-20
 */
@Data
@Builder
public class LicenseCategoryHelperDTO {

  private DevOpsCategory devOpsCategory;
  private CompanyLicense companyLicense;
}

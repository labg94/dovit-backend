package com.dovit.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 21-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseResume {

  private Long id;
  private String memberName;
  private String memberLastName;
  private Long companyId;
  private String companyName;
  private Boolean active;
  private String profiles;
  private Long availableStatusId;
  private String availableStatusDescription;
  private Long activeProjectsQty;
  private Long allProjectsQty;
}

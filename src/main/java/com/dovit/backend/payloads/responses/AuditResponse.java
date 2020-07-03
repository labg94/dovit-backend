package com.dovit.backend.payloads.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 16-05-20
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuditResponse {

  private Long id;
  private String actionDate;
  private String userId;
  private String userFullName;
  private String userEmail;

  private String status;
  private String message;

  private String data;
}

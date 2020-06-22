package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 22-06-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AzureAuthRequest {
  private String tokenId;
  private String accessToken;
}

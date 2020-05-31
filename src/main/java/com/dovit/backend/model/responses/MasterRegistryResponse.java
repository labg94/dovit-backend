package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 23-05-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterRegistryResponse {

  private Long id;
  private String description;
  private String observation;
}

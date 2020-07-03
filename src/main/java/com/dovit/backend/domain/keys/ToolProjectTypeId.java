package com.dovit.backend.domain.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Ramón París
 * @since 08-06-20
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ToolProjectTypeId implements Serializable {
  private Long toolId;
  private Long projectTypeId;
}

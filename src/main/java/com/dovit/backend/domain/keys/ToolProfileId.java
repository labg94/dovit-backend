package com.dovit.backend.domain.keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolProfileId implements Serializable {

  private Long memberId;
  private Long toolId;
  private Long levelId;
}

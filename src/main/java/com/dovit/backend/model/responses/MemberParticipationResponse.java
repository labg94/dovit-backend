package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberParticipationResponse {

  private List<ToolProfileResponse> tools;
  private String devopsCategoryName;
}

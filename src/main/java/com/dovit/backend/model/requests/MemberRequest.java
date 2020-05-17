package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequest {

  private long id;

  @NotNull(message = "Company cannot be empty")
  private long companyId;

  @NotEmpty(message = "Member name cannot be empty")
  private String name;

  @NotEmpty(message = "Member last name cannot be empty")
  private String lastName;

  private Boolean active;

  private List<Long> profiles;

  private List<ToolProfileRequest> toolProfile;
}

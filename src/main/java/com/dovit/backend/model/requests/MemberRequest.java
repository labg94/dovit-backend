package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequest {

  private long id;

  @NotNull
  private long companyId;

  @NotBlank private String name;

  @NotBlank private String lastName;

  private Boolean active;

  private List<Long> profiles;

  private List<ToolProfileRequest> toolProfile;
}

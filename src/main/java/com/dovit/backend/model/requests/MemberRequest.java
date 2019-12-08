package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequest {

  private long id;

  @NotBlank private long companyId;

  @NotBlank private String name;

  @NotBlank private String lastName;

  private List<Long> profiles;

  private List<ToolProfileRequest> toolProfileRequest;
}

package com.dovit.backend.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Ramón París
 * @since 09-12-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequest {

  private Long id;

  @NotEmpty(message = "Name is a mandatory field")
  private String name;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @NotNull(message = "Remember to select a start date")
  private LocalDate start;

  private String observation;

  @NotNull(message = "Company is a mandatory field")
  private Long companyId;

  private Boolean finished;
  private List<ProjectMemberRequest> members;
}

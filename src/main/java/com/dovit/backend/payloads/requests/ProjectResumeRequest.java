package com.dovit.backend.payloads.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author Ramón París
 * @since 27-06-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResumeRequest {

  @NotNull(message = "Project id is a mandatory field")
  private Long projectId;

  @NotEmpty(message = "Name is a mandatory field")
  private String name;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @NotNull(message = "Remember to select a start date")
  private LocalDate start;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  @Nullable
  private LocalDate endDate;

  private String observation;

  @NotNull private Boolean finished;
}

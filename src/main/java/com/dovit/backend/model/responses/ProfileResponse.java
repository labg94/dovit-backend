package com.dovit.backend.model.responses;

import com.dovit.backend.domain.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 08-12-2019
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProfileResponse {

  private Long id;
  private String description;

  public ProfileResponse(Profile profile) {
    this.id = profile.getId();
    this.description = profile.getDescription();
  }
}

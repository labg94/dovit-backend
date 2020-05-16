package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramón París
 * @since 10-12-2019
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LevelResponse {

    private Long levelId;
    private String description;
}

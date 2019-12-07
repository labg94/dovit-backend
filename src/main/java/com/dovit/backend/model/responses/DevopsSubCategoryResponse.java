package com.dovit.backend.model.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Ramón París
 * @since 14-10-2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevopsSubCategoryResponse {

    private Long subcategoryId;
    private String subcategoryName;
    private List<ToolResponse> tools;
}

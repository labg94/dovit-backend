package com.dovit.backend.payloads.responses;

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
public class DevopsCategoryResponse {

    private Long categoryId;
    private String categoryName;
    private List<DevopsSubCategoryResponse> subcategories;
}

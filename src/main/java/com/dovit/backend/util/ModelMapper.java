package com.dovit.backend.util;

import com.dovit.backend.domain.*;
import com.dovit.backend.model.requests.CompanyLicenseRequest;
import com.dovit.backend.model.requests.UserRequest;
import com.dovit.backend.model.responses.DevopsCategoryResponse;
import com.dovit.backend.model.responses.DevopsSubCategoryResponse;
import com.dovit.backend.model.responses.ToolResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
public class ModelMapper {

    public static void mapUserRequestToUser(UserRequest request, User user){
        user.setId(request.getId());
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setActive(request.getActive());
    }

    public static List<DevopsCategoryResponse> mapDevOpsCategoryToResponse(List<DevOpsCategory> categories){
        return categories.stream().map(c -> {
            DevopsCategoryResponse category = new DevopsCategoryResponse();
            category.setCategoryId(c.getId());
            category.setCategoryName(c.getDescription());
            category.setSubcategories(c.getSubcategories().stream().map(sub -> {
                DevopsSubCategoryResponse subCategory = new DevopsSubCategoryResponse();
                subCategory.setSubcategoryId(sub.getId());
                subCategory.setSubcategoryName(sub.getDescription());
                subCategory.setTools(sub.getTools().stream().map(t -> new ToolResponse(t.getId(), t.getName(), t.getImageUrl())).collect(Collectors.toList()));
                return subCategory;
            }).collect(Collectors.toList()));
            return category;
        }).collect(Collectors.toList());
    }

    public static void mapCompanyLicenseRequestToCompanyLicense(CompanyLicenseRequest request, CompanyLicense companyLicense, Company company, License license){
        companyLicense.setStartDate(request.getStartDate().toInstant());
        if (request.getExpirationDate() != null) {
            companyLicense.setExpirationDate(request.getExpirationDate().toInstant());
        }
        companyLicense.setCompany(company);
        companyLicense.setLicense(license);
    }

}

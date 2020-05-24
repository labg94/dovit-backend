package com.dovit.backend.util;

import com.dovit.backend.model.requests.CompanyLicenseRequest;
import com.dovit.backend.model.requests.CompanyRequest;
import com.dovit.backend.model.requests.LicenseRequest;
import com.dovit.backend.model.requests.ToolRequest;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

/**
 * @author Ramón París
 * @since 12-04-20
 */
public class RequestBuilderUtil<T> {

  public static CompanyLicenseRequest companyLicenseRequest =
      CompanyLicenseRequest.builder()
          .id(1L)
          .licenseId(1L)
          .companyId(1L)
          .startDate(LocalDate.now())
          .expirationDate(LocalDate.now())
          .build();

  private static final Gson gson = new Gson();

  public static CompanyRequest companyRequest =
      CompanyRequest.builder().id(1L).name("RetailSBS").build();

  public static String getJson(String jsonName) {
    Path filePath = Paths.get("src", "test", "resources", "requests", jsonName + ".json");
    try {
      return Files.readString(filePath);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  public static ToolRequest getToolRequest() {
    return gson.fromJson(getJson("ToolRequest"), ToolRequest.class);
  }

  public static LicenseRequest getLicenseRequest() {
    return gson.fromJson(getJson("LicenseRequest"), LicenseRequest.class);
  }
}

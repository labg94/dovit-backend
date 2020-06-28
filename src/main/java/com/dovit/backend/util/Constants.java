package com.dovit.backend.util;

/**
 * @author Ramón París
 * @since 02-10-2019
 */
public class Constants {

  public static final Long ROLE_ADMIN_ID = 1L;
  public static final Long ROLE_CLIENT_ID = 2L;
  public static final Long ROLE_CLIENT_ADMIN_ID = 3L;

  public static final String ACCESS_DENIED = "Access is denied";

  public static final String CLEVER_IT = "Cleverit";

  public static final String AUDIT_STATUS_OK = "OK";
  public static final String AUDIT_STATUS_NOK = "NOT OK";
  public static final String AUDIT_STATUS_ERROR = "ERROR";

  public static final String TOOL_POINTS_LICENSE_TXT = "Company has an active license";
  public static final int TOOL_POINTS_LICENSE = 5;

  public static final String TOOL_POINTS_PROJECT_TYPE_TXT = "Project type '%s' matches";
  public static final int TOOL_POINTS_PROJECT_TYPE = 2;

  public static final String TOOL_POINTS_MEMBER_SENIOR_TXT = "Senior member knows it";
  public static final int TOOL_POINTS_MEMBER_SENIOR = 3;

  public static final String TOOL_POINTS_MEMBER_SEMI_SENIOR_TXT = "Semi-senior member knows it";
  public static final int TOOL_POINTS_MEMBER_SEMI_SENIOR = 2;

  public static final String TOOL_POINTS_MEMBER_JUNIOR_TXT = "Junior member knows it";
  public static final int TOOL_POINTS_MEMBER_JUNIOR = 1;

  public static final String TOOL_POINTS_HISTORY_TXT = "Has been used before";
  public static final int TOOL_POINTS_HISTORY = 1;

  public static final String TOOL_POINTS_EXISTS_TXT = "Exists in %s category";
  public static final int TOOL_POINTS_EXISTS = 1;
  public static final String AZURE_ACCESS = "AZURE_ACCESS";
  public static final String DOVIT_ACCESS = "DOVIT_ACCESS";
}

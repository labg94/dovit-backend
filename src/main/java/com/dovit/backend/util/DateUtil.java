package com.dovit.backend.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Ramón París
 * @since 09-05-20
 */
public class DateUtil {

  private static final DateTimeFormatter OUTPUT_DATE_FORMAT = DateTimeFormatter.ISO_DATE;
  private static final DateTimeFormatter OUTPUT_DATETIME_FORMAT = DateTimeFormatter.ISO_DATE_TIME;

  public static String formatDateToString(LocalDate localDate) {
    return localDate.format(OUTPUT_DATE_FORMAT);
  }

  public static String formatDateToString(LocalDateTime localDate) {
    return localDate.format(OUTPUT_DATETIME_FORMAT);
  }
}

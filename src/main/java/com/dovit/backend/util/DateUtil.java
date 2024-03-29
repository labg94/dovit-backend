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
    if (localDate != null) {
      return localDate.format(OUTPUT_DATE_FORMAT);
    }
    return null;
  }

  public static String formatDateToString(LocalDateTime localDate) {
    if (localDate != null) {
      return localDate.format(OUTPUT_DATETIME_FORMAT);
    }
    return null;
  }

  public static boolean isBetween(LocalDate date, LocalDate from, LocalDate to) {
    if (date != null) {
      return date.compareTo(from) >= 0 && to == null
          || (date.compareTo(from) >= 0 && date.compareTo(to) <= 0);
    }

    return false;
  }
}

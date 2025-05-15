package org.soptcollab.web1.hyundaicard.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class SystemPrintLoggingUtil implements LoggingUtil {

  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  @Override
  public void info(final String message, final Object... data) {
    String dateTime = LocalDateTime.now().format(formatter);

    StringBuilder log = new StringBuilder();

    log.append(dateTime)
        .append(" [INFO]: ")
        .append(message);

    if (data != null && data.length > 0) {
      log.append("\n| data: ")
          .append(Arrays.toString(data));
    }

    System.out.println(log);
  }

  @Override
  public void error(final Exception exception) {
    String dateTime = LocalDateTime.now().format(formatter);
    StringBuilder log = new StringBuilder();

    String message = exception.getMessage();
    StackTraceElement[] stackTraceElements = exception.getStackTrace();

    log.append(dateTime)
        .append(" [ERROR]: ")
        .append(message)
        .append("\n| stackTrace: ")
        .append(stackTraceElements[0]);

    System.err.println(log);
  }
}

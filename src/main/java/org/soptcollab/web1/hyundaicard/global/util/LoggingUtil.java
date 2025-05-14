package org.soptcollab.web1.hyundaicard.global.util;


public interface LoggingUtil {

  public void info(String message, Object... data);

  public void error(Exception exception);
}

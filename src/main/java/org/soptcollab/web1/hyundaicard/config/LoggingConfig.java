package org.soptcollab.web1.hyundaicard.config;

import org.soptcollab.web1.hyundaicard.util.LoggingUtil;
import org.soptcollab.web1.hyundaicard.util.SystemPrintLoggingUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

  @Bean
  public LoggingUtil loggingUtil() {
    return new SystemPrintLoggingUtil();
  }
}

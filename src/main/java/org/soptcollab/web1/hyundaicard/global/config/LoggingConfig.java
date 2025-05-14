package org.soptcollab.web1.hyundaicard.global.config;

import org.soptcollab.web1.hyundaicard.global.util.LoggingUtil;
import org.soptcollab.web1.hyundaicard.global.util.SystemPrintLoggingUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

  @Bean
  public LoggingUtil loggingUtil() {
    return new SystemPrintLoggingUtil();
  }
}

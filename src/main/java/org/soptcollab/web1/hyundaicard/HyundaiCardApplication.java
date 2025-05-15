package org.soptcollab.web1.hyundaicard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HyundaiCardApplication {

  public static void main(String[] args) {
    SpringApplication.run(HyundaiCardApplication.class, args);
  }

}

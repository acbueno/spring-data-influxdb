package br.com.acbueno.lodon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringDataInfluxdbApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringDataInfluxdbApplication.class, args);
  }

}

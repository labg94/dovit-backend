package com.dovit.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = {DovitBackendApplication.class, Jsr310JpaConverters.class})
public class DovitBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DovitBackendApplication.class, args);
    }
}

package com.example.vash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VashApplication {

	public static void main(String[] args) {
		SpringApplication.run(VashApplication.class, args);
	}

}

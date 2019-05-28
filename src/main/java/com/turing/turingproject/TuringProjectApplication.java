package com.turing.turingproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TuringProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TuringProjectApplication.class, args);
	}

}

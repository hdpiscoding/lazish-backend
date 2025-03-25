package com.lazish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LazishApplication {

	public static void main(String[] args) {
		SpringApplication.run(LazishApplication.class, args);
	}

}

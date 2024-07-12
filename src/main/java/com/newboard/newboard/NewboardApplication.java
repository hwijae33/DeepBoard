package com.newboard.newboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class NewboardApplication {

	public static void main(String[] args) {

		SpringApplication.run(NewboardApplication.class, args);
	}

}

package com.jackson.educen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class EducenApplication {

	public static void main(String[] args) {
		SpringApplication.run(EducenApplication.class, args);
	}

}

package com.jackson.educen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class EduCenApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduCenApplication.class, args);
	}

}

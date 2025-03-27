package com.neura.logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.neura","com.user"})
@EnableMongoRepositories(basePackages = {"com.neura.repo","com.user.repo"})  // ✅ Explicitly scan repositories
public class NeuraApiProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeuraApiProjectApplication.class, args);
	}

}

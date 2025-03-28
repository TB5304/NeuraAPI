package com.neura.logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NeuraApiProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeuraApiProjectApplication.class, args);
	}

}

//@ComponentScan(basePackages = {"com.neura","com.user"})
//@EnableMongoRepositories(basePackages = {"com.neura.repo","com.user.repo"}) 
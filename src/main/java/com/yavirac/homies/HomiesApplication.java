package com.yavirac.homies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EntityScan
@EnableJpaRepositories
@SpringBootApplication
public class HomiesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomiesApplication.class, args);
	}

}

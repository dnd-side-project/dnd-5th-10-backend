package com.dnd10.iterview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class IterviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(IterviewApplication.class, args);
	}

}

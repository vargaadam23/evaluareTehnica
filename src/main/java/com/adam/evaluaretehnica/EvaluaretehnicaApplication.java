package com.adam.evaluaretehnica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EvaluaretehnicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvaluaretehnicaApplication.class, args);
	}

}

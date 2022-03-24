package com.capgemini.NightFoxReview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class NightFoxReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(NightFoxReviewApplication.class, args);
	}

	@GetMapping
	public String hello(){
		return "I am from test";
	}

}

package com.capgemini.NightFoxReview;

import com.capgemini.NightFoxReview.model.Review;
import com.capgemini.NightFoxReview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class NightFoxReviewApplication implements CommandLineRunner {
	@Autowired
	ReviewService reviewService;
	public static void main(String[] args) {

		SpringApplication.run(NightFoxReviewApplication.class, args);



	}



	@Override
	public void run(String... args) throws Exception {
		reviewService.addReviewByArtistId(1L, new Review("Micky", "Good job!", true, 1L));

	}
}

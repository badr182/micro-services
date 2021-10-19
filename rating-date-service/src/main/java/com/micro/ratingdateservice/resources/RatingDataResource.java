package com.micro.ratingdateservice.resources;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.ratingdateservice.models.Rating;
import com.micro.ratingdateservice.models.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingDataResource {
	
	@GetMapping("/{movieId}")
	public Rating getRating(@PathVariable("movieId") String movieId) {
		return new Rating(movieId, 4);
	}
	
	
	@GetMapping("users/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String userId) {
		List<Rating> rating = Arrays.asList(
				new Rating("1234", 4),
				new Rating("5678", 4)
				);
		UserRating userRating = new UserRating();
		userRating.setUserRating(rating);
		return userRating;
	}
}

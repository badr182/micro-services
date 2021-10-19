package com.micro.moviecatalogservice.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.micro.moviecatalogservice.models.CatalogItem;
import com.micro.moviecatalogservice.models.Movie;
import com.micro.moviecatalogservice.models.Rating;
import com.micro.moviecatalogservice.models.UserRating;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){															   
		UserRating ratings = restTemplate.getForObject("http://rating-data-service/ratingsdata/users/"+userId,  UserRating.class);
		return ratings.getUserRating().stream().map(rating ->{
			Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+ rating.getMovieId(), Movie.class);			
			return new CatalogItem(movie.getName(), "Desc", rating.getRating());
		})
				.collect(Collectors.toList()) ;
		
//		return Collections.singletonList(
//				new CatalogItem("Transformers", "Test", 4)
//				);				
	}
}



//Movie movie = webClientBuilder.build()
//.get()
//.uri("http://localhost:8082/movies/"+ rating.getMovieId())
//.retrieve()
//.bodyToMono(Movie.class)
//.block();
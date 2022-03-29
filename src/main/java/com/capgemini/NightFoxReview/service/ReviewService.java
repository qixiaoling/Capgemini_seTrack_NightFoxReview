package com.capgemini.NightFoxReview.service;

import com.capgemini.NightFoxReview.Exception.BadRequestException;
import com.capgemini.NightFoxReview.Exception.NotFoundException;
import com.capgemini.NightFoxReview.model.Review;
import com.capgemini.NightFoxReview.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;
    @Autowired
    WebClient.Builder webClientBuilder;


    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAllReviews() {
        List<Review> reviewList = new ArrayList<>();
        reviewRepository.findAll().forEach(reviewList::add);
        return reviewList;
    }

    public Review getReviewById(Long id) {
        Optional<Review> possibleReview = reviewRepository.findById(id);
        if(possibleReview.isPresent()){
            return possibleReview.get();
        }
        throw new NotFoundException(
                "Review id: " + id + "does not exist.");
    }

    public List<Review> getReviewsByArtistId(Long id){
        return reviewRepository.findByArtistId(id);
    }

    public void addReviewByArtistId(Long artistId, Review review){
       webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/artist/checkExists/" + artistId)
                    .retrieve()
              .onStatus(
                        HttpStatus.INTERNAL_SERVER_ERROR::equals,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
              .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(BadRequestException::new))
              .onStatus(
                        HttpStatus.NOT_FOUND::equals,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(NotFoundException::new))
              .bodyToMono(String.class)
              .doOnSuccess(s -> {
                  reviewRepository.save(review);
              })
//              .subscribe(System.out::println);
              .block();
       return ;

//              .toEntity(HttpStatus.class)
//              .map(response -> response.getStatusCode());

    }
    public void updateReviewById(Long id, Review review) {
        Optional<Review> possibleReview = reviewRepository.findById(id);
        if(possibleReview.isPresent()){
            possibleReview.get().setNameReviewer(review.getNameReviewer());
            possibleReview.get().setDescription(review.getDescription());
            possibleReview.get().setLike(review.getLike());
            reviewRepository.save(possibleReview.get());
            return;
        }
        throw new NotFoundException(
                "Review id: " + id + "does not exist.");

    }

    public void deleteReviewById(Long reviewId){
        Optional<Review> possibleReview = reviewRepository.findById(reviewId);
        if(possibleReview.isPresent()){
            reviewRepository.deleteById(reviewId);
            return;
        }
        throw new NotFoundException(
                "Review id: " + reviewId + "does not exist.");
    }



}

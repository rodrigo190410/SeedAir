package com.arqui.seedair.controllers;

import com.arqui.seedair.dtos.ReviewRegisterDTO;
import com.arqui.seedair.dtos.ReviewResponseDTO;
import com.arqui.seedair.entities.Review;
import com.arqui.seedair.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/seedair") // http://localhost:8080/seedair
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    //Registrar reseñas -> para el cliente
    @PostMapping("/reviews/register") // http://localhost:8080/seedair/reviews/register
    public ResponseEntity<ReviewRegisterDTO> register(@RequestBody ReviewRegisterDTO reviewRegister){
        ReviewRegisterDTO newReview = reviewService.register(reviewRegister);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseDTO>> listReviews(){
        List<ReviewResponseDTO> listReviews = reviewService.listReviews();
        return new ResponseEntity<>(listReviews, HttpStatus.OK);
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<List<ReviewResponseDTO>> listReviewsByCustomerId(@PathVariable Long id){
        List<ReviewResponseDTO> listReviewsByCustomer = reviewService.listById(id);
        return new ResponseEntity<>(listReviewsByCustomer, HttpStatus.OK);
    }

    @PutMapping("/reviews/update")
    public ResponseEntity<Review> updateReviewVisibility(@RequestBody Review review){
        Review updatedReview = reviewService.updateVisibility(review);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id){
        reviewService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

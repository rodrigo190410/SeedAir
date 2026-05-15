package com.arqui.seedair.controllers;

import com.arqui.seedair.dtos.ReviewRegisterDTO;
import com.arqui.seedair.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

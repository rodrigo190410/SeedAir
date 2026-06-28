package com.arqui.seedair.services;

import com.arqui.seedair.dtos.ReviewRegisterDTO;
import com.arqui.seedair.dtos.ReviewResponseDTO;
import com.arqui.seedair.entities.Review;

import java.util.List;

public interface ReviewService {
    public Review add(Review review);
    public ReviewRegisterDTO register(ReviewRegisterDTO reviewRegister);
    public Review findById(Long id);
    public List<Review> listAll();
    public List<ReviewResponseDTO> listReviews();
    public List<ReviewResponseDTO> listById(Long id);
    public Review updateVisibility(Review review);
    public void delete(Long id);
}

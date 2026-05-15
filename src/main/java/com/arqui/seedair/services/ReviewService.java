package com.arqui.seedair.services;

import com.arqui.seedair.dtos.ReviewRegisterDTO;
import com.arqui.seedair.entities.Review;

public interface ReviewService {
    public Review add(Review review);
    public ReviewRegisterDTO register(ReviewRegisterDTO reviewRegister);
}

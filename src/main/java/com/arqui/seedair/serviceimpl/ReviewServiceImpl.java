package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.ReviewRegisterDTO;
import com.arqui.seedair.entities.Customer;
import com.arqui.seedair.entities.Reservation;
import com.arqui.seedair.entities.Review;
import com.arqui.seedair.repositories.CustomerRepository;
import com.arqui.seedair.repositories.ReviewRepository;
import com.arqui.seedair.services.ReservationService;
import com.arqui.seedair.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ReservationService reservationService;
    @Override
    public Review add(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public ReviewRegisterDTO register(ReviewRegisterDTO reviewRegister) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByUser_username(username);

        Reservation reservation = reservationService.findById(reviewRegister.getReservationId());
        Review newReview = new Review(
                null,
                reviewRegister.getRating(),
                reviewRegister.getComment(),
                true, LocalDate.now(), customer,
                reservation
        );

        reviewRepository.save(newReview);
        return reviewRegister;
    }
}

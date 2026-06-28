package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.ReviewRegisterDTO;
import com.arqui.seedair.dtos.ReviewResponseDTO;
import com.arqui.seedair.entities.Customer;
import com.arqui.seedair.entities.Reservation;
import com.arqui.seedair.entities.Review;
import com.arqui.seedair.exceptions.ResourceNotFoundException;
import com.arqui.seedair.repositories.CustomerRepository;
import com.arqui.seedair.repositories.ReviewRepository;
import com.arqui.seedair.services.ReservationService;
import com.arqui.seedair.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Review findById(Long id) {
        return reviewRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("La reseña con el id: "+ id+ " no existe"));
    }

    @Override
    public List<Review> listAll() {
        return reviewRepository.findAll();
    }

    @Override
    public List<ReviewResponseDTO> listReviews() {
        List<Review> list=listAll();
        List<ReviewResponseDTO> dtoList = new ArrayList<>();

        for (Review r:list){
            ReviewResponseDTO dto = new ReviewResponseDTO(
                    r.getRating(), r.getComment(),
                    r.getIsVisible(), r.getCreatedAt(),
                    r.getReservation().getId()
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<ReviewResponseDTO> listById(Long id) {
        return reviewRepository.findReviewByCustomerId(id);
    }

    @Override
    public Review updateVisibility(Review review) {
        Review foundReview = findById(review.getId());
        foundReview.setIsVisible(review.getIsVisible());
        reviewRepository.save(foundReview);
        return review;
    }

    @Override
    public void delete(Long id) {

        reviewRepository.deleteById(id);
    }
}

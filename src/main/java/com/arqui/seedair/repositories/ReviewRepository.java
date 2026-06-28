package com.arqui.seedair.repositories;

import com.arqui.seedair.dtos.ReviewResponseDTO;
import com.arqui.seedair.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select new com.arqui.seedair.dtos.ReviewResponseDTO(r.rating,r.comment, r.isVisible, r.createdAt, r.reservation.id)" +
            "from Review r where r.customer.id = ?1" )
    List<ReviewResponseDTO>findReviewByCustomerId(Long id);
}

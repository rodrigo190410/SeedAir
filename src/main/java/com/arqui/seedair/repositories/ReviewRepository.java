package com.arqui.seedair.repositories;

import com.arqui.seedair.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}

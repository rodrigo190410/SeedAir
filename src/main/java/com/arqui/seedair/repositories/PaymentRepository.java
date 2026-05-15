package com.arqui.seedair.repositories;

import com.arqui.seedair.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

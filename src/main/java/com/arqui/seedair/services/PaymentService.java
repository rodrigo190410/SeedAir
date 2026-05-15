package com.arqui.seedair.services;

import com.arqui.seedair.dtos.PaymentUpdateDTO;
import com.arqui.seedair.entities.Payment;

public interface PaymentService {
    public Payment add(Payment payment);
    public Payment findById(Long id);
    public PaymentUpdateDTO update(PaymentUpdateDTO paymentUpdate);
}

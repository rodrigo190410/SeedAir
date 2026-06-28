package com.arqui.seedair.services;

import com.arqui.seedair.dtos.PaymentResponseDTO;
import com.arqui.seedair.dtos.PaymentUpdateDTO;
import com.arqui.seedair.entities.Payment;

import java.util.List;

public interface PaymentService {
    public Payment add(Payment payment);
    public Payment findById(Long id);
    public PaymentUpdateDTO update(PaymentUpdateDTO paymentUpdate);
    public List<Payment> listAll();
    public List<PaymentResponseDTO> listPayments();
}

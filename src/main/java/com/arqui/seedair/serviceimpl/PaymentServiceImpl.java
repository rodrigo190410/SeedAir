package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.PaymentUpdateDTO;
import com.arqui.seedair.entities.Payment;
import com.arqui.seedair.repositories.PaymentRepository;
import com.arqui.seedair.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;



    @Override
    public Payment add(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment findById(Long id) {
        return paymentRepository.findById(id).get();
    }

    @Override
    public PaymentUpdateDTO update(PaymentUpdateDTO paymentUpdate) {

        Payment foundPayment = findById(paymentUpdate.getId());
        foundPayment.setPaymentStatus(paymentUpdate.getPaymentStatus());
        foundPayment.setPaymentMethod(paymentUpdate.getPaymentMethod());

        paymentRepository.save(foundPayment);
        return paymentUpdate;
    }


}

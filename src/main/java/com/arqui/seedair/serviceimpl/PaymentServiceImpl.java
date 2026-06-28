package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.PaymentResponseDTO;
import com.arqui.seedair.dtos.PaymentUpdateDTO;
import com.arqui.seedair.entities.Payment;
import com.arqui.seedair.exceptions.ResourceNotFoundException;
import com.arqui.seedair.repositories.PaymentRepository;
import com.arqui.seedair.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        return paymentRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("El pago con ID " + id + " no existe."));
    }

    @Override
    public PaymentUpdateDTO update(PaymentUpdateDTO paymentUpdate) {

        Payment foundPayment = findById(paymentUpdate.getId());
        foundPayment.setIsPending(paymentUpdate.getIsPending());
        foundPayment.setPaymentMethod(paymentUpdate.getPaymentMethod());

        paymentRepository.save(foundPayment);
        return paymentUpdate;
    }

    @Override
    public List<Payment> listAll() {
        return paymentRepository.findAll();
    }

    @Override
    public List<PaymentResponseDTO> listPayments() {
        List<Payment> paymentList = listAll();
        List<PaymentResponseDTO> dtoList = new ArrayList<>();
        for (Payment p : paymentList){
            PaymentResponseDTO dto = new PaymentResponseDTO(p.getPaymentDate(),p.getAmount(),
                    p.getPaymentMethod(),p.getIsPending(),p.getOperationCode());
            dtoList.add(dto);
        }
        return dtoList;
    }
}

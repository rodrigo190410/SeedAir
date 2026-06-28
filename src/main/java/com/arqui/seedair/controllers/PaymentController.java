package com.arqui.seedair.controllers;

import com.arqui.seedair.dtos.PaymentResponseDTO;
import com.arqui.seedair.dtos.PaymentUpdateDTO;
import com.arqui.seedair.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/seedair") // http://localhost:8080/seedair
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PutMapping("/payments/set")  // http://localhost:8080/seedair/payments/set
    public ResponseEntity<PaymentUpdateDTO> setStatus(@RequestBody PaymentUpdateDTO paymentUpdate){
            PaymentUpdateDTO updatedPayment = paymentService.update(paymentUpdate);
            return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }

    @GetMapping("/payments")
    public ResponseEntity<List<PaymentResponseDTO>> listPayments(){
        List<PaymentResponseDTO> list = paymentService.listPayments();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
}

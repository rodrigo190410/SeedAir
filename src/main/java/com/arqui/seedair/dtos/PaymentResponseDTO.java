package com.arqui.seedair.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private LocalDate paymentDate;
    private Double amount;
    private String paymentMethod;
    private Boolean isPending;
    private String operationCode;
}

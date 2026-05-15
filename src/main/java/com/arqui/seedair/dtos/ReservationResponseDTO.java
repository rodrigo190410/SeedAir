package com.arqui.seedair.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDTO {

    private LocalDate scheduledStartDate;
    private Double totalAmount;
    private String status;
    private Long customerId;
    private Long parcelId;
}

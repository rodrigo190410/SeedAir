package com.arqui.seedair.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRangeDateDTO {

    private Long id;
    private LocalDate scheduledStartDate;
    private Double hectares;
    private Double totalAmount;
    private String status; // PENDING, ON GOING, CANCELLED

}

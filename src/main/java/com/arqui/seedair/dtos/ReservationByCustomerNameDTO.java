package com.arqui.seedair.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReservationByCustomerNameDTO {
    private Long id;
    private LocalDate scheduledStartDate;
    private LocalDate scheduledEndDate;
    private Long customer;
    private String customerName;
    private String customerLastName;

}

package com.arqui.seedair.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationByStatusDTO {
    private LocalDate scheduledStartDate;
    private LocalDate scheduledEndDate;
    private String customerName;
    private Boolean isActive;
}
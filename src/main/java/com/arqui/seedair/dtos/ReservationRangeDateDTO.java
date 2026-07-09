package com.arqui.seedair.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Boolean isActive;
    private LocalDate scheduledEndDate;

}

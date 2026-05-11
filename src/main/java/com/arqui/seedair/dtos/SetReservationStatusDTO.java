package com.arqui.seedair.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetReservationStatusDTO {
    private Long id;
    private String customerName;
    private LocalDate scheduledStartDate;
    private  String status;

}
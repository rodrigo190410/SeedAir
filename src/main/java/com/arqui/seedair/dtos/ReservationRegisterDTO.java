package com.arqui.seedair.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRegisterDTO {
    private LocalDate scheduledStartDate;
    private LocalDate scheduledEndDate;
    private Long parcelId;
    private Long droneId;
}


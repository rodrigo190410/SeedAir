package com.arqui.seedair.dtos;

import com.arqui.seedair.entities.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTOByCustomerId {

    private Long id;
    private LocalDate scheduledStartDate;
    private LocalDate scheduledEndDate;
    private Double hectares;
    private Double totalAmount;

    private Reservation.ReservationState state;

    private String parcelLocation;

    private Long operatorId;

    private String droneModel;

    private Long paymentId;


    private Double reviewRating;

}

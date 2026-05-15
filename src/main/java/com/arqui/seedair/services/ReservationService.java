package com.arqui.seedair.services;

import com.arqui.seedair.dtos.*;
import com.arqui.seedair.entities.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    public Reservation add(Reservation reservation);
    public List<Reservation> listAll();
    public List<ReservationResponseDTO> listReservations();
    public List<ReservationRangeDateDTO> listByReservationRangeDTO(LocalDate startFilter, LocalDate endFilter);
    public List<Reservation> listByCustomerName(String name);
    public List<ReservationByCustomerNameDTO> listReservationByCustomerDTO(String name);
    public ReservationRegisterDTO registerReservation(ReservationRegisterDTO reservationRegisterDTO);
    public Reservation findById(Long id);
    public List<Reservation> listByStatus(String status);
    public List<ReservationByStatusDTO> listByStatusDTO(String status);
    public SetReservationStatusDTO updateStatus(SetReservationStatusDTO updatedStatus);

    List<Reservation> getReservationsByUsername(String username);
    //funcionalidad para eliminar una reserva, como admin
    void delete(Long id);




}

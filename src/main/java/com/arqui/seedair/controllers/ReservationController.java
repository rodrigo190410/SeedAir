package com.arqui.seedair.controllers;

import com.arqui.seedair.dtos.*;
import com.arqui.seedair.entities.Reservation;
import com.arqui.seedair.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin("*")
@RequestMapping("/seedair") // http://localhost:8080/seedair
public class ReservationController {
    @Autowired
    ReservationService reservationService;

    //registrar una reserva -> para el cliente
    @PostMapping("/reservations/register") // http://localhost:8080/seedair/reservations/register
    public ResponseEntity<ReservationRegisterDTO> registerReservation(
            @RequestBody ReservationRegisterDTO reservationRegisterDTO
    ){
        ReservationRegisterDTO newRegister = reservationService.registerReservation(reservationRegisterDTO);
        return new ResponseEntity<>(newRegister, HttpStatus.OK);
    }
    //Lista general de reservas -> admin
    @GetMapping("/reservations") // http://localhost:8080/seedair/reservations
    public ResponseEntity<List<ReservationResponseDTO>> listAll(){
        List<ReservationResponseDTO> reservationList = reservationService.listReservations();
        return new ResponseEntity<>(reservationList, HttpStatus.OK);
    };

    //Lista de reservas en rango de fechas -> para el admin
    @GetMapping("/reservations/{startFilter}/{endFilter}") // http://localhost:8080/seedair/reservations/{startFilter}/{endFilter}
    public ResponseEntity<List<ReservationRangeDateDTO>> listByRangeDate(
            @PathVariable LocalDate startFilter,
            @PathVariable LocalDate endFilter
    ){
        List<ReservationRangeDateDTO> newList = reservationService.listByReservationRangeDTO(startFilter, endFilter);
        return new ResponseEntity<>(newList, HttpStatus.OK);
    };

    //Lista de reservas por primer nombre cliente -> para el admin
    @GetMapping("/reservations/{name}") // http://localhost:8080/seedair/reservations/{name}
    public ResponseEntity<List<ReservationByCustomerNameDTO>> listReservationsByCustomerName(@PathVariable String name){
        List<ReservationByCustomerNameDTO> newList = reservationService.listReservationByCustomerDTO(name);
        return new ResponseEntity<>(newList, HttpStatus.OK);
    }

    //Lista de reservas por estado -> para el admin
    @GetMapping("/reservations/status/{isActive}") //http://localhost:8080/seedair/reservations/status/isActive
    public ResponseEntity<List<ReservationByStatusDTO>> listReservationByStatus(@PathVariable Boolean isActive){
        List<ReservationByStatusDTO> reservationByStatusList = reservationService.listByStatusDTO(isActive);
        return new ResponseEntity<>(reservationByStatusList, HttpStatus.OK);
    }

    //Actualizar estado de una reserva -> para el admin
    @PutMapping("/reservations/updated_status") // http://localhost:8080/seedair/reservations/updated_status
    public ResponseEntity<SetReservationStatusDTO> updateStatus (@RequestBody SetReservationStatusDTO updatedStatus){
        SetReservationStatusDTO newStatus = reservationService.updateStatus(updatedStatus);
        return new ResponseEntity<>(newStatus, HttpStatus.OK);
    }


    //Lista las reservas activas por un username -> para el admin
    @GetMapping("/reservations/active")// http://localhost:8080/seedair/reservations/active
    public ResponseEntity<List<Reservation>> getActiveReservationsByUsername(Authentication authentication){
        List<Reservation> actives = reservationService.getReservationsByUsername(authentication.getName());
        return new ResponseEntity<>(actives, HttpStatus.OK);
    }


    //eliminar una reserva mediante id
    @DeleteMapping("/delete/{id}") // http://localhost:8080/seedair/delete/1
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id){
        reservationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
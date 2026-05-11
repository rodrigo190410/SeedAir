package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.*;
import com.arqui.seedair.entities.Customer;
import com.arqui.seedair.entities.Parcel;
import com.arqui.seedair.entities.Reservation;
import com.arqui.seedair.repositories.ReservationRepository;
import com.arqui.seedair.services.CustomerService;
import com.arqui.seedair.services.ParcelService;
import com.arqui.seedair.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    CustomerService customerService;
    @Autowired
    ParcelService parcelService;

    @Override
    public Reservation add(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> listAll() {
        return reservationRepository.findAll();
    }

    @Override
    public List<ReservationRangeDateDTO> listByReservationRangeDTO(LocalDate startFilter, LocalDate endFilter) {
        List<Reservation> reservationList = reservationRepository.findByScheduledStartDateBetween(startFilter, endFilter);
        List<ReservationRangeDateDTO> reservationRangeDateDTOList = new ArrayList<>();

        for (Reservation r:reservationList){

            reservationRangeDateDTOList.add(new ReservationRangeDateDTO(
                    r.getId(), r.getScheduledStartDate(), r.getHectares(),r.getTotalAmount(),
                    r.getStatus()
            ));
        }

        return reservationRangeDateDTOList;
    }

    @Override
    public List<Reservation> listByCustomerName(String name) {
        return reservationRepository.findByCustomerName(name);
    }

    @Override
    public List<ReservationByCustomerNameDTO> listReservationByCustomerDTO(String name) {
        List<Reservation> reservationList = listByCustomerName(name);
        List<ReservationByCustomerNameDTO> reservationDTOList = new ArrayList<>();

        for (Reservation r: reservationList){
            reservationDTOList.add(new ReservationByCustomerNameDTO(
                    r.getId(), r.getScheduledStartDate(), r.getScheduledEndDate(),
                    r.getCustomer().getId(), r.getCustomer().getFirstName(),
                    r.getCustomer().getLastName()
            ));
        }
        return reservationDTOList;
    }

    @Override
    public ReservationRegisterDTO registerReservation(ReservationRegisterDTO reservationRegisterDTO) {

        Reservation newReservation = new Reservation();

        newReservation.setId(reservationRegisterDTO.getId());
        newReservation.setScheduledStartDate(reservationRegisterDTO.getScheduledStartDate());
        newReservation.setScheduledEndDate(reservationRegisterDTO.getScheduledEndDate());
        newReservation.setHectares(reservationRegisterDTO.getHectares());
        Customer customer = customerService.findById(reservationRegisterDTO.getCustomerId());
        Parcel parcel = parcelService.findById(reservationRegisterDTO.getParcelId());

        newReservation.setCustomer(customer);
        newReservation.setParcel(parcel);

        newReservation.setStatus("PENDING");
        newReservation.setRatePerHectare(150.0);
        newReservation.setTotalAmount(reservationRegisterDTO.getHectares() * 150.0);
        newReservation.setPayments(new ArrayList<>());


        reservationRepository.save(newReservation);

        return reservationRegisterDTO;
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).get();
    }

    @Override
    public List<Reservation> listByStatus(String status) {
        return reservationRepository.findByStatus(status);
    }

    @Override
    public List<ReservationByStatusDTO> listByStatusDTO(String status) {
        List<Reservation> reservationList = listByStatus(status);
        List<ReservationByStatusDTO> listDto = new ArrayList<>();
        for (Reservation r:reservationList){
            listDto.add(new ReservationByStatusDTO(
                    r.getScheduledStartDate(), r.getScheduledEndDate(),
                    r.getCustomer().getFirstName(), r.getStatus()
            ));
        }
        return listDto;
    }

    @Override
    public SetReservationStatusDTO updateStatus(SetReservationStatusDTO updatedStatus) {
        Reservation foundReservation = findById(updatedStatus.getId());
        foundReservation.setStatus(updatedStatus.getStatus());
        Reservation savedReservation = reservationRepository.save(foundReservation);
        SetReservationStatusDTO newDTO = new SetReservationStatusDTO();
        newDTO.setId(savedReservation.getId());
        newDTO.setStatus(savedReservation.getStatus());
        newDTO.setCustomerName(savedReservation.getCustomer().getFirstName());
        newDTO.setScheduledStartDate(savedReservation.getScheduledStartDate());

        return newDTO;
    }

    @Override
    public List<Reservation> getReservationsByUsername(String username) {
        return reservationRepository.findReservationByUsername(username);
    }

    @Override
    public void delete(Long id) {
        //verificar si existe
        if (!reservationRepository.existsById(id)) {
            throw new NoSuchElementException("No se encontro la reserva con ese id:" + id);
        }
        reservationRepository.deleteById(id);
    }

}

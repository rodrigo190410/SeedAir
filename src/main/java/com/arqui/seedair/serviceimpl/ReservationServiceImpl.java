package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.*;
import com.arqui.seedair.entities.*;
import com.arqui.seedair.exceptions.ResourceNotFoundException;
import com.arqui.seedair.repositories.*;
import com.arqui.seedair.services.CustomerService;
import com.arqui.seedair.services.ParcelService;
import com.arqui.seedair.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    CustomerRepository customerRepository;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    ParcelService parcelService;
    @Autowired
    OperatorRepository operatorRepository;
    @Autowired
    DroneRepository droneRepository;
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

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByUser_username(username);
        Parcel parcel = parcelService.findById(reservationRegisterDTO.getParcelId());
        Double hectares= parcel.getTotalHectares();
        Double ratePerHectare = 0.0;
        if (hectares <= 6) {
            ratePerHectare = 75.0;
        } else {
            ratePerHectare = 50.0;
        }

        //Añadir validacion si hay operador disponible
        Operator operator = operatorRepository.findById(reservationRegisterDTO.getOperatorId()).get();
        //Añadir validacion si hay dron disponible
        Drone drone = droneRepository.findById(reservationRegisterDTO.getDroneId()).get();

        Double totalAmount= hectares*ratePerHectare;

        Reservation newReservation = new Reservation(
                null, reservationRegisterDTO.getScheduledStartDate(), reservationRegisterDTO.getScheduledEndDate(),
                hectares, ratePerHectare,totalAmount, "PENDIENTE", null,
                new ArrayList<>(), customer, parcel, operator, drone
        );

        reservationRepository.save(newReservation);

        LocalDate paymentDate = newReservation.getScheduledEndDate().plusDays(1);

        Payment initialPayment = new Payment(
                null, paymentDate, totalAmount, "AL CONTADO", "PENDIENTE",
                null, newReservation
        );
        Payment savedPayment = paymentRepository.save(initialPayment);
        savedPayment.setOperationCode("OP-" + savedPayment.getId());
        paymentRepository.save(savedPayment);

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

   /* @Override
    public void delete(Long id) {
        //verificar si existe
        if (!reservationRepository.existsById(id)) {
            throw new NoSuchElementException("No se encontro la reserva con ese id:" + id);
        }
        reservationRepository.deleteById(id);
    }*/

    @Override
    public void delete (Long id){
        if(!reservationRepository.existsById(id)){
            throw new ResourceNotFoundException("No se encontró la reserva con id:" + id);
        }
        reservationRepository.deleteById(id);
    }

}

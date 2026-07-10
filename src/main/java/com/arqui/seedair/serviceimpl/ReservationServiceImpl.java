package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.*;
import com.arqui.seedair.entities.*;
import com.arqui.seedair.exceptions.InvalidDataRangeException;
import com.arqui.seedair.exceptions.ResourceNotFoundException;
import com.arqui.seedair.repositories.*;
import com.arqui.seedair.services.CustomerService;
import com.arqui.seedair.services.OperatorService;
import com.arqui.seedair.services.ParcelService;
import com.arqui.seedair.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    CustomerService customerService;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    ParcelService parcelService;
    @Autowired
    OperatorRepository operatorRepository;
    @Autowired
    DroneRepository droneRepository;
    @Autowired
    private OperatorService operatorService;

    @Override
    public Reservation add(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> listAll() {
        return reservationRepository.findAll();
    }

    @Override
    public List<ReservationResponseDTO> listReservations() {
        List<Reservation> reservationList = listAll();
        List<ReservationResponseDTO> newList = new ArrayList<>();

        for (Reservation r:reservationList){
            ReservationResponseDTO dto = new ReservationResponseDTO(
                    r.getScheduledStartDate(),
                    r.getTotalAmount(),
                    r.getIsActive(), r.getCustomer().getId(),
                    r.getParcel().getId()
            );
            newList.add(dto);
        }
        return newList;
    }

    @Override
    public List<ReservationRangeDateDTO> listByReservationRangeDTO(LocalDate startFilter, LocalDate endFilter) {
        List<Reservation> reservationList = reservationRepository.findByScheduledStartDateBetween(startFilter, endFilter);
        List<ReservationRangeDateDTO> reservationRangeDateDTOList = new ArrayList<>();

        for (Reservation r:reservationList){

            reservationRangeDateDTOList.add(new ReservationRangeDateDTO(
                    r.getId(), r.getScheduledStartDate(), r.getHectares(),r.getTotalAmount(),
                    r.getIsActive(), r.getScheduledEndDate()
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

        if (reservationRegisterDTO.getParcelId() == null ||
                reservationRegisterDTO.getDroneId() == null) {
            throw new ResourceNotFoundException("No se puede registrar: La parcela y el dron son obligatorios.");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer customer = customerRepository.findByUser_username(username);
        Parcel parcel = parcelService.findById(reservationRegisterDTO.getParcelId());
        Double hectares = parcel.getTotalHectares();
        Double ratePerHectare = 0.0;
        if (hectares <= 6) {
            ratePerHectare = 75.0;
        } else {
            ratePerHectare = 50.0;
        }

        Drone drone = droneRepository.findById(reservationRegisterDTO.getDroneId()).get();

        if (!drone.getIsActive()) {
            throw new InvalidDataRangeException(
                    "No se puede registrar la reserva: El dron seleccionado no se encuentra disponible."
            );
        }

        LocalDate startDate = reservationRegisterDTO.getScheduledStartDate();
        LocalDate endDate = reservationRegisterDTO.getScheduledEndDate();
        long cantDays = ChronoUnit.DAYS.between(startDate, endDate);
        if (cantDays < 0) {
            throw new InvalidDataRangeException("La fecha de fin no puede ser anterior a la de inicio");
        }
        if (cantDays == 0) {
            throw new InvalidDataRangeException("Mínimo debe realizar una reserva por un día");
        }

        // Auto-asignación de operador disponible en ese rango de fechas
        Operator operator = findAvailableOperatorForDates(startDate, endDate);

        Double totalAmount = hectares * ratePerHectare * cantDays;

        if (drone.getDroneModel() != null && drone.getDroneModel().getSeedCapacityKg() != null && drone.getDroneModel().getSeedCapacityKg() > 20) {
            totalAmount += 50.0;
        }

        if (drone.getReservations() != null) {
            for (Reservation r : drone.getReservations()) {
                if (r.getIsActive()) {
                    if (!startDate.isAfter(r.getScheduledEndDate()) && !endDate.isBefore(r.getScheduledStartDate())) {
                        throw new InvalidDataRangeException("El dron seleccionado ya se encuentra reservado en las fechas indicadas.");
                    }
                }
            }
        }

        List<Customer> customerList = customerService.listAll();
        for (Customer c : customerList) {
            for (Reservation r : c.getReservations()) {
                if (r.getIsActive()) {
                    if (!startDate.isAfter(r.getScheduledStartDate()) && !endDate.isBefore(r.getScheduledEndDate())) {
                        throw new InvalidDataRangeException("Ya existe una reserva registrada dentro de estas fechas");
                    }
                }
            }
        }

        Reservation newReservation = new Reservation(
                null, reservationRegisterDTO.getScheduledStartDate(), reservationRegisterDTO.getScheduledEndDate(),
                hectares, ratePerHectare, totalAmount, Reservation.ReservationState.PENDIENTE, true, null,
                null, customer, parcel, operator, drone
        );

        reservationRepository.save(newReservation);

        LocalDate paymentDate = newReservation.getScheduledEndDate();

        Payment initialPayment = new Payment(
                null, paymentDate, totalAmount, "AL CONTADO", true,
                null, newReservation
        );
        Payment savedPayment = paymentRepository.save(initialPayment);
        savedPayment.setOperationCode("OP-" + savedPayment.getId());
        paymentRepository.save(savedPayment);

        return reservationRegisterDTO;
    }

    private Operator findAvailableOperatorForDates(LocalDate startDate, LocalDate endDate) {
        List<Operator> operators = operatorRepository.findAll();

        for (Operator op : operators) {
            if (op.getAvailabilityStatus() == null || !op.getAvailabilityStatus()) {
                continue; // el operador está marcado como no disponible en general
            }

            boolean tieneConflicto = false;
            if (op.getReservations() != null) {
                for (Reservation r : op.getReservations()) {
                    if (r.getIsActive() != null && r.getIsActive()) {
                        if (!startDate.isAfter(r.getScheduledEndDate()) && !endDate.isBefore(r.getScheduledStartDate())) {
                            tieneConflicto = true;
                            break;
                        }
                    }
                }
            }

            if (!tieneConflicto) {
                return op; // primer operador libre en ese rango de fechas
            }
        }

        throw new InvalidDataRangeException(
                "No hay operadores disponibles para las fechas seleccionadas."
        );
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id).get();
    }

    @Override
    public List<Reservation> listByStatus(Boolean isActive) {
        return reservationRepository.findByIsActive(isActive);
    }

    @Override
    public List<ReservationByStatusDTO> listByStatusDTO(Boolean isActive) {
        List<Reservation> reservationList = listByStatus(isActive);
        List<ReservationByStatusDTO> listDto = new ArrayList<>();
        for (Reservation r:reservationList){
            listDto.add(new ReservationByStatusDTO(
                    r.getScheduledStartDate(), r.getScheduledEndDate(),
                    r.getCustomer().getFirstName(), r.getIsActive()
            ));
        }
        return listDto;
    }

    @Override
    public SetReservationStatusDTO updateStatus(SetReservationStatusDTO updatedStatus) {
        Reservation foundReservation = findById(updatedStatus.getId());
        foundReservation.setIsActive(updatedStatus.getIsActive());
        Reservation savedReservation = reservationRepository.save(foundReservation);
        SetReservationStatusDTO newDTO = new SetReservationStatusDTO();
        newDTO.setId(savedReservation.getId());
        newDTO.setIsActive(savedReservation.getIsActive());
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

    @Override
    public List<ReservationDTOByCustomerId> listReservationsByCustomerId(Long customerId) {
        List<Reservation> customerReservationsActive = reservationRepository.findByIsActive(true);


        List<ReservationDTOByCustomerId> customerReservationsToShow = customerReservationsActive.stream()
                .filter(r -> r.getIsActive() != null && r.getCustomer().getId().equals(customerId))//pra que jale solo a los activos y del customer
                .map(
                        r->new ReservationDTOByCustomerId(
                                r.getId(),
                                r.getScheduledStartDate(),
                                r.getScheduledEndDate(),
                                r.getHectares(),
                                r.getTotalAmount(),
                                r.getState(),
                                r.getParcel() != null ? r.getParcel().getLocationText() : "Sin ubicación",
                                r.getOperator() != null ? r.getOperator().getId() : null,
                                (r.getDrone() != null && r.getDrone().getDroneModel() != null) ? r.getDrone().getDroneModel().getModelName() : "Sin asignar",
                                r.getPayment() != null ? r.getPayment().getId() : null,
                                r.getReview() != null ? r.getReview().getRating() : null
                        )).toList();
        return customerReservationsToShow;
    }
    @Override
    public List<ReservationDTOByCustomerId> listReservationsByCustomer() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Customer currentCustomer = customerRepository.findByUser_username(username);

        List<Reservation> reservationsActive = reservationRepository.findByIsActive(true);


        List<ReservationDTOByCustomerId> customerReservationsToShow = reservationsActive.stream()
                .filter(r -> r.getIsActive() != null && r.getCustomer().getId().equals(currentCustomer.getId()))//pra que jale solo a los activos y del customer
                .map(
                        r->new ReservationDTOByCustomerId(
                                r.getId(),
                                r.getScheduledStartDate(),
                                r.getScheduledEndDate(),
                                r.getHectares(),
                                r.getTotalAmount(),
                                r.getState(),
                                r.getParcel() != null ? r.getParcel().getLocationText() : "Sin ubicación",
                                r.getOperator() != null ? r.getOperator().getId() : null,
                                (r.getDrone() != null && r.getDrone().getDroneModel() != null) ? r.getDrone().getDroneModel().getModelName() : "Sin asignar",
                                r.getPayment() != null ? r.getPayment().getId() : null,
                                r.getReview() != null ? r.getReview().getRating() : null
                        )).toList();
        return customerReservationsToShow;
    }

}

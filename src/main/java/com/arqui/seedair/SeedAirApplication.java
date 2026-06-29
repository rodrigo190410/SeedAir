package com.arqui.seedair;

import com.arqui.seedair.services.*;
import com.arqui.seedair.dtos.DroneDTO;
import com.arqui.seedair.dtos.DroneModelDTO;
import com.arqui.seedair.dtos.UserDTO;
import com.arqui.seedair.entities.*;
import com.arqui.seedair.repositories.DroneRepository;
import com.arqui.seedair.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootApplication
public class SeedAirApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeedAirApplication.class, args);
    }

    @Bean
    public CommandLineRunner startConfiguration(
            ReservationService reservationService,
            CustomerService customerService,
            UserService userService,
            ParcelService parcelService,
            AuthorityService authorityService,
            DroneModelService droneModelService,
            DroneService droneService,
            ReviewService reviewService,
            OperatorService operatorService,
            MaintenanceService maintenanceService,
            PaymentService paymentService,
            DroneBrandService droneBrandService
    ) {
        return args -> {

            Authority authority1 = authorityService.add(new Authority(null, "ADMIN", null));
            Authority authority2 = authorityService.add(new Authority(null, "CUSTOMER", null));

            //Data de prueba
            userService.addDTO(new UserDTO(null, "brunocustomer", "pass", "CUSTOMER"));
            userService.addDTO(new UserDTO(null, "luismanager", "pass", "ADMIN"));
            userService.addDTO(new UserDTO(null, "adrianacustomer", "pass", "CUSTOMER"));
            //Brands
            droneBrandService.add(new DroneBrand(null, "DJI", null));
            droneBrandService.add(new DroneBrand(null, "Parrot", null));
            droneBrandService.add(new DroneBrand(null, "Potensic", null));
            //Data de prueba de modelos de drones usando DTO
            droneModelService.addDroneModel(new DroneModel(null, "DJI Agras T40",
                    40.0, 20.0, 180, 30.0, null, droneBrandService.findById(1L)
            ));
//            droneModelService.addDTO(new DroneModelDTO("DJI Agras T20P", "DJI", 20.0, 15));

//            //insertar drones fisico
//            droneService.addDTO(new DroneDTO("SN-001", 1L)); // Drone libre
//            Drone droneOcupado = droneService.addDTO(new DroneDTO("SN-002", 2L)); // Drone para Luis

            // vincular el dron a la reserva (ID 2 de Luis Miguel)
           /* Reservation luisReserva = reservationService.findById(2L);
            if (luisReserva != null) {
                luisReserva.setDrone(droneOcupado);
                reservationService.add(luisReserva);
            }*/

            // Drone en mantenimiento (Vínculado al T40 - ID 1)
            droneService.addDrone(new Drone(null, "DRN-001",
                    "SN-MANTO-01", LocalDate.of(2026, 03, 11), true, null,
                    null, droneModelService.findById(1L)
            ));

//            // Drone inactivo/fuera de servicio (Vínculado al T20P - ID 2)
//            Drone droneInactivo = droneService.addDTO(new DroneDTO("SN-OFF-99", 2L));
//            droneInactivo.setCurrentStatus("INACTIVE");
//            droneRepository.save(droneInactivo);
//
//            // Drone nuevo en revisión técnica (Vínculado al T40 - ID 1)
//            Drone droneRevision = droneService.addDTO(new DroneDTO("SN-CHECK-05", 1L));
//            droneRevision.setCurrentStatus("MAINTENANCE");
//            droneRepository.save(droneRevision);

            customerService.add(new Customer(
                    null, "Bruno", "Guerrero", 999666333, userService.findById(1L), null,
                    null, null
            ));
//            customerService.add(new Customer(
//                    null, "Luis Miguel", "Rojas", 123456789, userService.findById(2L), null,
//                    null, null
//            ));
            customerService.add(new Customer(
                    null, "Adriana", "Tapia", 987654321, userService.findById(3L), null,
                    null, null
            ));
            parcelService.add(new Parcel(null, "Cajarmaca - Sector Condorillo Alto", 2.5, -13.4589,
                    -76.1325, LocalDate.of(2026, 04, 30),
                    null, customerService.findById(1L), true
            ));
//            parcelService.add(new Parcel(null, "Changuillo - San Javier", 4.5, -28.4589,
//                    -50.1325,LocalDate.of(2026,04,25),
//                    null, customerService.findById(2L)
//            ));
//
//            parcelService.add(new Parcel(null, "Huaral - Fundo Los Olivos", 6.0, -43.4753,
//                    -25.4004,LocalDate.of(2026,04,04),
//                    null, customerService.findById(3L)
//            ));


            operatorService.add(new Operator(
                    null, "DRN-AGRO-1024",
                    "AVANZADO",
                    5, true,
                    null
            ));
//            operatorService.add(new Operator(
//                    null, "DRN-AGRO-2055",
//                    "Manejo de drones de siembra intermedia",
//                    3, false,
//                    null
//            ));
//            operatorService.add(new Operator(
//                    null, "DRN-AGRO-4012",
//                    "Manejo de drones de siembra avanzada",
//                    6, true,
//                    null
//            ));

            maintenanceService.add(new Maintenance(
                    null, LocalDate.of(2026, 05, 30), null,
                    true, "Revision semestral", 150.50, droneService.findById(1L)
            ));
//            maintenanceService.add(new Maintenance(
//                    null, LocalDate.of(2026,05,19), null,
//                    "SCHEDULED", "Cambio de hélices principales y limpieza", 200.50, droneService.findById(2L)
//            ));
//            maintenanceService.add(new Maintenance(
//                    null, LocalDate.of(2026,03,21), LocalDate.of(2026,03,23),
//                    "COMPLETED", "Revisión trimestral preventiva de motores", 125.00, droneService.findById(3L)
//            ));

            reservationService.add(new Reservation(
                    null, LocalDate.of(2026, 05, 16),
                    LocalDate.of(2026, 05, 16),
                    1.5, 75.0, 112.5, true, null, null, customerService.findById(1L),
                    parcelService.findById(1L), operatorService.findById(1L), droneService.findById(1L)
            ));

//            reservationService.add(new Reservation(
//                    null, LocalDate.of(2026, 05,22),
//                    LocalDate.of(2026, 05,24),
//                    3.5, 100.0, 350.0, "PENDING",null, null, customerService.findById(2L),
//                    parcelService.findById(2L), operatorService.findById(2L), null
//            ));
//
//            reservationService.add(new Reservation(
//                    null, LocalDate.of(2026, 05,26),
//                    LocalDate.of(2026, 05,28),
//                    6.0, 83.33, 500.0, "CANCELLED",null, null, customerService.findById(3L),
//                    parcelService.findById(3L), operatorService.findById(3L), null
//            ));

            reviewService.add(new Review(
                    null, 5.0, "Excelente servicio y atención",
                    true, LocalDate.of(2026, 05, 19),
                    customerService.findById(1L), reservationService.findById(1L)
            ));
//            reviewService.add(new Review(
//                    null, 4.5, "La experiencia fue buena, aunque hubo demora",
//                    true, LocalDate.of(2026,05,25),
//                    customerService.findById(2L), reservationService.findById(2L)
//            ));
//            reviewService.add(new Review(
//                    null, 1.0, "Muy mala experiencia",
//                    false, LocalDate.of(2026,05,29),
//                    customerService.findById(3L), reservationService.findById(3L)
//            ));


            paymentService.add(new Payment(
                    null, LocalDate.of(2026, 05, 17), 112.50, "EFECTIVO",
                    true, "OP-1", reservationService.findById(1L)
            ));
//            paymentService.add(new Payment(
//                    null, LocalDate.of(2026,05,19) , 250.0, "CRÉDITO",
//                    "COMPLETO", "0002", reservationService.findById(1L)
//            ));
//            paymentService.add(new Payment(
//                    null, LocalDate.of(2026,05,21) , 100.0, "CONTADO",
//                    "COMPLETO", "0003", reservationService.findById(2L)
//            ));
        };
    }
}


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
            userService.addDTO(new UserDTO(null, "customer", "pass", "CUSTOMER"));
            userService.addDTO(new UserDTO(null, "admin", "pass", "ADMIN"));
            //usuario en mantenimiento -> no usar
            userService.addDTO(new UserDTO(null, "universal", "pass", "ADMIN;CUSTOMER"));
            //Brands
            droneBrandService.add(new DroneBrand(null, "DJI", null));
            droneBrandService.add(new DroneBrand(null, "Parrot", null));
            droneBrandService.add(new DroneBrand(null, "Potensic", null));
            //Data de prueba de modelos de drones usando DTO
            droneModelService.addDroneModel(new DroneModel(null, "DJI Agras T40",
                    40.0, 20.0, 180, 30.0, null, droneBrandService.findById(1L),true
            ));
            droneModelService.addDroneModel(new DroneModel(null, "DJI Agras T20P",
                    40.0, 20.0, 180, 30.0, null, droneBrandService.findById(1L),true
            ));
            droneModelService.addDroneModel(new DroneModel(null, "Parrot Bluegrass Fields",
                    40.0, 20.0, 180, 30.0, null, droneBrandService.findById(1L),true
            ));
            droneModelService.addDroneModel(new DroneModel(null, "DJI Agras T50",
                    40.0, 20.0, 180, 30.0, null, droneBrandService.findById(1L),true
            ));
            droneModelService.addDroneModel(new DroneModel(null, "DJI Agras T10",
                    40.0, 20.0, 180, 30.0, null, droneBrandService.findById(1L),true
            ));
            droneModelService.addDroneModel(new DroneModel(null, "XAG P100 Pro",
                    40.0, 20.0, 180, 30.0, null, droneBrandService.findById(1L),true
            ));
            droneModelService.addDroneModel(new DroneModel(null, "XAG V40",
                    40.0, 20.0, 180, 30.0, null, droneBrandService.findById(1L),true
            ));
            droneModelService.addDroneModel(new DroneModel(null, "DJI Mavic 3M",
                    40.0, 20.0, 180, 30.0, null, droneBrandService.findById(1L),true
            ));
            droneModelService.addDroneModel(new DroneModel(null, "eBee Ag SenseFly",
                    40.0, 20.0, 180, 30.0, null, droneBrandService.findById(1L),true
            ));




            // Drone en mantenimiento (Vínculado al T40 - ID 1)
            droneService.addDrone(new Drone(null, "DRN-001",
                    "SN-MANTO-01", LocalDate.of(2026, 03, 11), true, null,
                    null, droneModelService.findById(1L)
            ));
            droneService.addDrone(new Drone(null, "DRN-002",
                    "SN-MANTO-02", LocalDate.of(2026, 06, 28), true, null,
                    null, droneModelService.findById(2L)
            ));
            droneService.addDrone(new Drone(null, "DRN-003",
                    "SN-MANTO-03", LocalDate.of(2026, 07, 18), false, null,
                    null, droneModelService.findById(3L)
            ));
            droneService.addDrone(new Drone(null, "DRN-004",
                    "SN-MANTO-04", LocalDate.of(2026, 04, 22), true, null,
                    null, droneModelService.findById(4L)
            ));
            droneService.addDrone(new Drone(null, "DRN-005",
                    "SN-MANTO-05", LocalDate.of(2026, 11, 10), true, null,
                    null, droneModelService.findById(5L)
            ));
            droneService.addDrone(new Drone(null, "DRN-006",
                    "SN-MANTO-06", LocalDate.of(2025, 03, 11), true, null,
                    null, droneModelService.findById(6L)
            ));
            droneService.addDrone(new Drone(null, "DRN-007",
                    "SN-MANTO-07", LocalDate.of(2025, 07, 21), false, null,
                    null, droneModelService.findById(7L)
            ));
            droneService.addDrone(new Drone(null, "DRN-008",
                    "SN-MANTO-08", LocalDate.of(2026, 12, 27), true, null,
                    null, droneModelService.findById(8L)
            ));
            droneService.addDrone(new Drone(null, "DRN-009",
                    "SN-MANTO-09", LocalDate.of(2026, 05, 20), true, null,
                    null, droneModelService.findById(9L)
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
                    -76.1325,-15.3421,
                    -28.3245, LocalDate.of(2026, 04, 30),
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
                    "SENIOR","Juan","Pérez",
                    5, true,true,
                    null
            ));
            operatorService.add(new Operator(
                    null, "DRN-AGRO-1000",
                    "SENIOR","Santiago","Arriola",
                    5, true,true,
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
                    1.5, 75.0, 112.5, Reservation.ReservationState.FINALIZADO , true, null, null, customerService.findById(1L),
                    parcelService.findById(1L), operatorService.findById(1L), droneService.findById(1L)
            ));

            reservationService.add(new Reservation(
                    null, LocalDate.of(2026, 07, 16),
                    LocalDate.of(2026, 05, 16),
                    1.5, 75.0, 112.5, Reservation.ReservationState.PENDIENTE , true, null, null, customerService.findById(1L),
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


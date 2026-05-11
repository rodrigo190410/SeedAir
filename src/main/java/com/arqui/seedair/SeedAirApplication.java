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
            @Autowired
            ReservationService reservationService,
            @Autowired
            CustomerService customerService,
            @Autowired
            UserService userService,
            @Autowired
            ParcelService parcelService,
            @Autowired
            AuthorityService authorityService,
            @Autowired
            DroneModelService droneModelService,
            @Autowired
            DroneService droneService,
            @Autowired
            DroneRepository droneRepository

    ){
        return args -> {

            Authority authority1 = authorityService.add(new Authority(null,"ROLE_ADMIN",null));
            Authority authority2 = authorityService.add(new Authority(null,"ROLE_USER",null));
            Authority authority3 = authorityService.add(new Authority(null,"ROLE_ASSIST",null));

            //Data de prueba
            userService.addDTO(new UserDTO(null, "brunouser", "pass", "ROLE_USER"));
            userService.addDTO(new UserDTO(null, "luisuser", "pass", "ROLE_ADMIN"));
            userService.addDTO(new UserDTO(null, "adrianauser", "pass", "ROLE_USER;ROLE_ASSIST"));

            //Data de prueba de modelos de drones usando DTO
            droneModelService.addDTO(new DroneModelDTO("DJI Agras T40", "DJI", 40.0, 20));
            droneModelService.addDTO(new DroneModelDTO("DJI Agras T20P", "DJI", 20.0, 15));

            //insertar drones fisico
            droneService.addDTO(new DroneDTO("SN-001", 1L)); // Drone libre
            Drone droneOcupado = droneService.addDTO(new DroneDTO("SN-002", 2L)); // Drone para Luis

            // vincular el dron a la reserva (ID 2 de Luis Miguel)
           /* Reservation luisReserva = reservationService.findById(2L);
            if (luisReserva != null) {
                luisReserva.setDrone(droneOcupado);
                reservationService.add(luisReserva);
            }*/

            // Drone en mantenimiento (Vínculado al T40 - ID 1)
            Drone droneManto = droneService.addDTO(new DroneDTO("SN-MANTO-01", 1L));
            droneManto.setCurrentStatus("MAINTENANCE");
            droneRepository.save(droneManto);

            // Drone inactivo/fuera de servicio (Vínculado al T20P - ID 2)
            Drone droneInactivo = droneService.addDTO(new DroneDTO("SN-OFF-99", 2L));
            droneInactivo.setCurrentStatus("INACTIVE");
            droneRepository.save(droneInactivo);

            // Drone nuevo en revisión técnica (Vínculado al T40 - ID 1)
            Drone droneRevision = droneService.addDTO(new DroneDTO("SN-CHECK-05", 1L));
            droneRevision.setCurrentStatus("MAINTENANCE");
            droneRepository.save(droneRevision);

            customerService.add(new Customer(
                    null, "Bruno", "Guerrero", 999666333, userService.findById(1L), null,
                    null, null
            ));
            customerService.add(new Customer(
                    null, "Luis Miguel", "Rojas", 123456789, userService.findById(2L), null,
                    null, null
            ));
            customerService.add(new Customer(
                    null, "Adriana", "Tapia", 987654321, userService.findById(3L), null,
                    null, null
            ));


            parcelService.add(new Parcel(null, "Cajarmaca - Sector Condorillo Alto", 2.5, -13.4589,
                    -76.1325,LocalDate.of(2026,04,30),
                    null, customerService.findById(1L)
            ));
            parcelService.add(new Parcel(null, "Changuillo - San Javier", 4.5, -28.4589,
                    -50.1325,LocalDate.of(2026,04,25),
                    null, customerService.findById(2L)
            ));

            parcelService.add(new Parcel(null, "Huaral - Fundo Los Olivos", 6.0, -43.4753,
                    -25.4004,LocalDate.of(2026,04,04),
                    null, customerService.findById(3L)
            ));


            reservationService.add(new Reservation(
                    null, LocalDate.of(2026, 05,16),
                    LocalDate.of(2026, 05,18),
                    1.5, 333.33, 500.0, "ON GOING",null, new ArrayList<>(), customerService.findById(1L),
                    null, null, null
            ));

            reservationService.add(new Reservation(
                    null, LocalDate.of(2026, 05,22),
                    LocalDate.of(2026, 05,24),
                    3.5, 100.0, 350.0, "PENDING",null, new ArrayList<>(), customerService.findById(2L),
                    null, null, null
            ));

            reservationService.add(new Reservation(
                    null, LocalDate.of(2026, 05,26),
                    LocalDate.of(2026, 05,28),
                    6.0, 83.33, 500.0, "CANCELLED",null, new ArrayList<>(), customerService.findById(3L),
                    null, null, null
            ));
        };
    }

}


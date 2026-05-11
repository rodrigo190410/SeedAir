package com.arqui.seedair.repositories;

import com.arqui.seedair.dtos.DroneAvailableDTO;
import com.arqui.seedair.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {

    @Query("SELECT new com.example.demo.dto.DroneAvailableDTO(m.modelName, m.brand, m.seedCapacityKg, m.autonomyMinutes) " +
            "FROM Drone d JOIN d.droneModel m " +
            "WHERE d.id NOT IN (SELECT r.drone.id FROM Reservation r WHERE r.drone IS NOT NULL AND r.status IN ('PENDING', 'ON GOING'))")
    List<DroneAvailableDTO> findAvailableDronesForClient();

    //consulta para ver que drones estan en mantenimiento o inactivos sin importar si tienen reservas
    @Query("SELECT d FROM Drone d WHERE d.currentStatus = :status")
    List<Drone> findByCurrentStatus(@Param("status") String status);
}

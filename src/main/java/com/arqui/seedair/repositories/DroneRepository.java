package com.arqui.seedair.repositories;

import com.arqui.seedair.dtos.DroneAvailableDTO;
import com.arqui.seedair.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {

    boolean existsByCode(String code);
    boolean existsBySerialNumber(String serialNumber);

    @Query("SELECT new com.arqui.seedair.dtos.DroneAvailableDTO(m.modelName, m.droneBrand.id, m.seedCapacityKg, m.autonomyMinutes) " +
            "FROM Drone d JOIN d.droneModel m " +
            "WHERE d.id NOT IN (SELECT r.drone.id FROM Reservation r WHERE r.isActive = true)")
    List<DroneAvailableDTO> findAvailableDronesForClient();

    //consulta para ver que drones estan en mantenimiento o inactivos sin importar si tienen reservas
    @Query("SELECT d FROM Drone d WHERE d.isActive = ?1")
    List<Drone> findDronesByStatus( Boolean isActive);
}

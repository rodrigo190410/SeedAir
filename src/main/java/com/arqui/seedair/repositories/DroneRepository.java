package com.arqui.seedair.repositories;

import com.arqui.seedair.dtos.DroneAvailableDTO;
import com.arqui.seedair.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {

    boolean existsByCode(String code);
    boolean existsBySerialNumber(String serialNumber);
    List<Drone> findByIsActive(Boolean isActive);

    @Query("SELECT new com.arqui.seedair.dtos.DroneAvailableDTO(d.id, m.modelName, m.droneBrand.id, m.seedCapacityKg, m.autonomyMinutes) " +
            "FROM Drone d JOIN d.droneModel m " +
            "WHERE d.id NOT IN (SELECT r.drone.id FROM Reservation r WHERE r.state = 'PENDIENTE' AND r.drone IS NOT NULL) " +
            "AND d.isActive = true")
    List<DroneAvailableDTO> findAvailableDronesForClient();

    //consulta para ver que drones estan en mantenimiento o inactivos sin importar si tienen reservas
    @Query("SELECT d FROM Drone d WHERE d.isActive = ?1")
    List<Drone> findDronesByStatus( Boolean isActive);


    //esta connsulta JPQL sirve para usar la fecha establecida como filtro y busacara todos los drones
    //(isactive=true) activos en ese rango de fecha
    @Query("SELECT d FROM Drone d WHERE d.isActive = true " +
            "AND d.id NOT IN (" +
            "   SELECT r.drone.id FROM Reservation r " +
            "   WHERE r.isActive = true " +
            "   AND (:startDate <= r.scheduledEndDate AND :endDate >= r.scheduledStartDate)" +
            ") " +
            "AND d.id NOT IN (" +
            "   SELECT m.drone.id FROM Maintenance m " +
            "   WHERE (:startDate <= m.endDate AND :endDate >= m.startDate)" +
            ")")
    List<Drone> findAvailableDronesByDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}

package com.arqui.seedair.repositories;

import com.arqui.seedair.entities.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
    Boolean existsByDroneIdAndStartDate(Long droneId, LocalDate startDate);
    Boolean existsByDroneIdAndIsFinishedFalse(Long droneId);
}

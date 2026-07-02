package com.arqui.seedair.repositories;

import com.arqui.seedair.dtos.DroneAvailableDTO;
import com.arqui.seedair.dtos.OperatorAvailableDTO;
import com.arqui.seedair.entities.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
    @Query("SELECT new com.arqui.seedair.dtos.OperatorAvailableDTO(o.id, o.licenseCode, o.certificationLevel, o.experienceYears) " +
            "FROM Operator o " +
            "WHERE o.availabilityStatus = true")
    List<OperatorAvailableDTO> findAvailableDronesForClient();

}

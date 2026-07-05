package com.arqui.seedair.repositories;

import com.arqui.seedair.entities.DroneModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneModelRepository extends JpaRepository<DroneModel, Long> {

    List<DroneModel> findByIsActive(Boolean isActive);

    //Para corroborar la existencia de algun uso de marca y pueda ser eliminado de manera fisica sin romper nada en cascada
    Boolean existsByDroneBrandId(Long droneBrandId);


}

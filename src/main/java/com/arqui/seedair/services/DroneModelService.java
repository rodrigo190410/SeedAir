package com.arqui.seedair.services;

import com.arqui.seedair.dtos.DroneModelDTO;
import com.arqui.seedair.entities.DroneModel;

public interface DroneModelService {

    public DroneModel addDroneModel(DroneModel droneModel);
    public DroneModelDTO addDTO(DroneModelDTO dto);
    void delete(Long id);
    public DroneModel findById(Long id);

}

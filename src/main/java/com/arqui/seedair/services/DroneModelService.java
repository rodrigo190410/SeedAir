package com.arqui.seedair.services;

import com.arqui.seedair.dtos.DroneModelDTO;
import com.arqui.seedair.entities.DroneModel;

import java.util.List;

public interface DroneModelService {

    public DroneModel addDroneModel(DroneModel droneModel);
    public DroneModelDTO addDTO(DroneModelDTO dto);
    public DroneModel findById(Long id);
    public List<DroneModel> listAll();
    public List<DroneModelDTO> listDroneModels();
    public DroneModel update(DroneModel droneModel);
    void delete(Long id);

}

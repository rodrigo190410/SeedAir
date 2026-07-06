package com.arqui.seedair.services;

import com.arqui.seedair.dtos.DroneModelListDTO;
import com.arqui.seedair.dtos.DroneModelDTO;
import com.arqui.seedair.entities.DroneModel;

import java.util.List;

public interface DroneModelService {

    public DroneModel addDroneModel(DroneModel droneModel);
    public DroneModelDTO addDTO(DroneModelDTO dto);
    public DroneModel findById(Long id);
    public List<DroneModel> listAll();
    public List<DroneModelListDTO> listDroneModels();
    public DroneModelDTO update(DroneModelDTO droneModel);
    void delete(Long id);
    void logicDelete(Long id);
    public DroneModelDTO getDroneModelById(Long droneModelId);
    public List<DroneModelListDTO> getDroneModelsByIsActive(Boolean isActive);

}

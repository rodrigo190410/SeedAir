package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.DroneModelDTO;
import com.arqui.seedair.entities.DroneModel;
import com.arqui.seedair.repositories.DroneModelRepository;
import com.arqui.seedair.services.DroneModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroneModelServiceImpl implements DroneModelService {


    @Autowired
    private DroneModelRepository droneModelRepository;



    @Override
    public DroneModel addDroneModel(DroneModel droneModel) {
        return droneModelRepository.save(droneModel);
    }

    @Override
    public DroneModel addDTO(DroneModelDTO dto) {
        DroneModel model= new DroneModel();
        model.setModelName(dto.getName());
        model.setBrand(dto.getBrand());
        model.setSeedCapacityKg(dto.getSeedCapacityKg());
        model.setAutonomyMinutes(dto.getAutonomyMinutes());
        return droneModelRepository.save(model);
    }
}

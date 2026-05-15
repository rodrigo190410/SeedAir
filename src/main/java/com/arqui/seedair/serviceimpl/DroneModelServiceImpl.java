package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.DroneDTO;
import com.arqui.seedair.dtos.DroneModelDTO;
import com.arqui.seedair.entities.Drone;
import com.arqui.seedair.entities.DroneModel;
import com.arqui.seedair.exceptions.ResourceNotFoundException;
import com.arqui.seedair.repositories.DroneModelRepository;
import com.arqui.seedair.services.DroneModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DroneModelServiceImpl implements DroneModelService {


    @Autowired
    private DroneModelRepository droneModelRepository;


    @Override
    public DroneModel addDroneModel(DroneModel droneModel) {
        return droneModelRepository.save(droneModel);
    }

    @Override
    public DroneModelDTO addDTO(DroneModelDTO dto) {

        DroneModel droneModel = new DroneModel(
                null,
                dto.getBrand(),
                dto.getModelName(),
                dto.getSeedCapacityKg(),
                dto.getCoverageHectaresPerDay(),
                dto.getAutonomyMinutes(),
                dto.getMaxSpeedKmh(),
                null
        );
        droneModelRepository.save(droneModel);
        return dto;
    }

    @Override
    public void delete(Long id) {
        if (!droneModelRepository.existsById(id)) {
            throw new NoSuchElementException("No se encontro el drone con ese id:" + id);
        }
        droneModelRepository.deleteById(id);
    }

    @Override
    public DroneModel findById(Long id) {
        return droneModelRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("El modelo de dron con ID " + id + " no existe."));
    }
}

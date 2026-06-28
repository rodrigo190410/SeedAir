package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.DroneDTO;
import com.arqui.seedair.dtos.DroneModelDTO;
import com.arqui.seedair.entities.Drone;
import com.arqui.seedair.entities.DroneBrand;
import com.arqui.seedair.entities.DroneModel;
import com.arqui.seedair.exceptions.IncompleteDataException;
import com.arqui.seedair.exceptions.InvalidDataRangeException;
import com.arqui.seedair.exceptions.KeyRepeatedDataExeception;
import com.arqui.seedair.exceptions.ResourceNotFoundException;
import com.arqui.seedair.repositories.DroneModelRepository;
import com.arqui.seedair.services.DroneBrandService;
import com.arqui.seedair.services.DroneModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DroneModelServiceImpl implements DroneModelService {
    @Autowired
    DroneModelRepository droneModelRepository;
    @Autowired
    DroneBrandService droneBrandService;
    @Override
    public DroneModel addDroneModel(DroneModel droneModel) {
        return droneModelRepository.save(droneModel);
    }

    @Override
    public DroneModelDTO addDTO(DroneModelDTO dto) {

        List<DroneModel> droneModelList = droneModelRepository.findAll();

        if (dto.getModelName() == null || dto.getModelName().isBlank()) {
            throw new IncompleteDataException("El nombre del modelo es obligatorio");
        }

        if (dto.getSeedCapacityKg() <= 0) {
            throw new InvalidDataRangeException("La capacidad debe ser mayor a 0");
        }

        for (DroneModel d: droneModelList){
            if (dto.getModelName().equals(d.getModelName())){
                throw new KeyRepeatedDataExeception("El modelo: " + dto.getModelName() + " ya está registrado");
            }
        }

        DroneBrand droneBrandId = droneBrandService.findById(dto.getDroneBrandId());
        if (droneBrandId == null){
            throw new ResourceNotFoundException("El dron con el id: " + dto.getDroneBrandId() + " no existe");
        }
        DroneModel droneModel = new DroneModel(
                null,
                dto.getModelName(),
                dto.getSeedCapacityKg(),
                dto.getCoverageHectaresPerDay(),
                dto.getAutonomyMinutes(),
                dto.getMaxSpeedKmh(),
                null,
                droneBrandId
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

    @Override
    public List<DroneModel> listAll() {
        return droneModelRepository.findAll();
    }

    @Override
    public List<DroneModelDTO> listDroneModels() {
        List<DroneModel> drones = listAll();
        List<DroneModelDTO> newList = new ArrayList<>();

        for (DroneModel d:drones){
            DroneModelDTO dto = new DroneModelDTO(d.getDroneBrand().getId(),d.getModelName(),
                    d.getSeedCapacityKg(),d.getCoverageHectaresPerDay(),d.getAutonomyMinutes(),
                    d.getMaxSpeedKmh());

            newList.add(dto);
        }

        return newList;
    }

    @Override
    public DroneModel update(DroneModel droneModel) {
        DroneModel foundModel = findById(droneModel.getId());
        foundModel.setModelName(droneModel.getModelName());
        foundModel.setSeedCapacityKg(droneModel.getSeedCapacityKg());
        foundModel.setCoverageHectaresPerDay(droneModel.getCoverageHectaresPerDay());
        foundModel.setAutonomyMinutes(droneModel.getAutonomyMinutes());
        foundModel.setMaxSpeedKmh(droneModel.getMaxSpeedKmh());

        droneModelRepository.save(foundModel);
        return droneModel;
    }
}

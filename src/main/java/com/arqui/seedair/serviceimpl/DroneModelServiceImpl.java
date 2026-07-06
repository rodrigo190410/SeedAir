package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.DroneModelListDTO;
import com.arqui.seedair.dtos.DroneModelDTO;
import com.arqui.seedair.entities.DroneBrand;
import com.arqui.seedair.entities.DroneModel;
import com.arqui.seedair.exceptions.IncompleteDataException;
import com.arqui.seedair.exceptions.InvalidDataRangeException;
import com.arqui.seedair.exceptions.KeyRepeatedDataExeception;
import com.arqui.seedair.exceptions.ResourceNotFoundException;
import com.arqui.seedair.repositories.DroneBrandRepository;
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
    @Autowired
    DroneBrandRepository droneBrandRepository;
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
                droneBrandId,
                true
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
    public List<DroneModelListDTO> listDroneModels() {
        List<DroneModel> drones = listAll();
        List<DroneModelListDTO> newList = new ArrayList<>();

        for (DroneModel d:drones){
            DroneModelListDTO dto = new DroneModelListDTO(d.getId(),d.getDroneBrand().getName(),d.getModelName(),
                    d.getSeedCapacityKg(),d.getCoverageHectaresPerDay(),d.getAutonomyMinutes(),
                    d.getMaxSpeedKmh());

            newList.add(dto);
        }

        return newList;
    }

    @Override
    public DroneModelDTO update(DroneModelDTO droneModelDTO) {

        DroneModel droneModel = droneModelRepository.findById(droneModelDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("El modelo de ID " + droneModelDTO.getId() + " no existe."));

        if (droneModelDTO.getModelName() == null || droneModelDTO.getModelName().isBlank()) {
            throw new IncompleteDataException("El nombre del modelo es obligatorio.");
        }

        if (droneModelDTO.getSeedCapacityKg() == null || droneModelDTO.getSeedCapacityKg() < 0) {
            throw new InvalidDataRangeException("La capacidad de semillas debe tener un valor positivo.");
        }

        if (droneModelDTO.getCoverageHectaresPerDay() == null || droneModelDTO.getCoverageHectaresPerDay() < 0) {
            throw new InvalidDataRangeException("La cobertura de hectáreas por día debe tener un valor positivo.");
        }

        droneModel.setModelName(droneModelDTO.getModelName());
        droneModel.setSeedCapacityKg(droneModelDTO.getSeedCapacityKg());
        droneModel.setCoverageHectaresPerDay(droneModelDTO.getCoverageHectaresPerDay());
        droneModel.setAutonomyMinutes(droneModelDTO.getAutonomyMinutes());
        droneModel.setMaxSpeedKmh(droneModelDTO.getMaxSpeedKmh());

        if (droneModelDTO.getDroneBrandId() != null &&
                !droneModel.getDroneBrand().getId().equals(droneModelDTO.getDroneBrandId())) {

            DroneBrand brand = droneBrandRepository.findById(droneModelDTO.getDroneBrandId())
                    .orElseThrow(() -> new ResourceNotFoundException("La marca especificada no existe."));
            droneModel.setDroneBrand(brand);
        }

        droneModelRepository.save(droneModel);

        return droneModelDTO;
    }
    @Override
    public void logicDelete(Long id) {
        DroneModel droneModel = droneModelRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("El modelo de dron con ID " + id + " no existe."));

        droneModel.setIsActive(false);
        droneModelRepository.save(droneModel);
    }
    public DroneModelDTO getDroneModelById(Long droneModelId){
        DroneModel droneModel = findById(droneModelId);
        DroneModelDTO droneModelDTO = new DroneModelDTO(
                droneModel.getId(),
                droneModel.getDroneBrand().getId(),
                droneModel.getModelName(),
                droneModel.getSeedCapacityKg(),
                droneModel.getCoverageHectaresPerDay(),
                droneModel.getAutonomyMinutes(),
                droneModel.getMaxSpeedKmh()
        );

        return droneModelDTO;

    }
    @Override
    public List<DroneModelListDTO> getDroneModelsByIsActive(Boolean isActive) {

        List<DroneModel> models = droneModelRepository.findByIsActive(isActive);


        return models.stream().map(model -> new DroneModelListDTO(
                model.getId(),
                model.getDroneBrand().getName(),
                model.getModelName(),
                model.getSeedCapacityKg(),
                model.getCoverageHectaresPerDay(),
                model.getAutonomyMinutes(),
                model.getMaxSpeedKmh()
        )).toList();
    }
}

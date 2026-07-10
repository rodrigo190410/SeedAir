package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.DroneAvailableDTO;
import com.arqui.seedair.dtos.DroneDTO;
import com.arqui.seedair.dtos.DroneDTOUpdate;
import com.arqui.seedair.dtos.DroneListDTO;
import com.arqui.seedair.entities.Drone;
import com.arqui.seedair.entities.DroneModel;
import com.arqui.seedair.exceptions.IncompleteDataException;
import com.arqui.seedair.exceptions.KeyRepeatedDataExeception;
import com.arqui.seedair.exceptions.ResourceNotFoundException;
import com.arqui.seedair.repositories.DroneModelRepository;
import com.arqui.seedair.repositories.DroneRepository;
import com.arqui.seedair.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DroneServiceImpl implements DroneService {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private DroneModelRepository droneModelRepository;

    @Override
    public Drone addDrone(Drone drone) {return droneRepository.save(drone);}


    @Override
    public List<DroneAvailableDTO> getAvailableDrones() {
        return droneRepository.findAvailableDronesForClient();
    }

    @Override
    public DroneDTO addDTO(DroneDTO dto) {

        if (droneRepository.existsByCode(dto.getCode())) {
            throw new KeyRepeatedDataExeception("Ya existe un dron con el código: " + dto.getCode());
        }

        if (droneRepository.existsBySerialNumber(dto.getSerialNumber())) {
            throw new KeyRepeatedDataExeception("Ya existe un dron con el número de serie: " + dto.getSerialNumber());
        }

        DroneModel model = droneModelRepository.findById(dto.getDroneModelId())
                .orElseThrow(() -> new ResourceNotFoundException("Modelo no encontrado"));

        Drone drone = new Drone(
                null,
                dto.getCode(),
                dto.getSerialNumber(),
                dto.getAcquisitionDate(),
                true,
                null,
                null,
                model
        );
        droneRepository.save(drone);
        return dto;
    }

    @Override
    public Drone findById(Long id) {
        return droneRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("El dron con ID " + id + " no existe."));
    }

    @Override
    public List<DroneListDTO> getDronesByIsActive(Boolean isActive) {
        List<Drone> dronesList = droneRepository.findByIsActive(isActive);

        return dronesList.stream().map(drone -> new DroneListDTO(
                drone.getId(),
                drone.getCode(),
                drone.getSerialNumber(),
                drone.getAcquisitionDate(),
                drone.getDroneModel().getModelName(),
                drone.getDroneModel().getDroneBrand().getName()
        )).toList();

    }

    @Override
    public DroneDTOUpdate update(DroneDTOUpdate droneDTO) {


        Drone drone = droneRepository.findById(droneDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("El dron con ID " + droneDTO.getId() + " no existe."));

        if (droneDTO.getCode() == null || droneDTO.getCode().isBlank()){
            throw new IncompleteDataException("El código es obligatorio");
        }

        if (!drone.getCode().equals(droneDTO.getCode()) && droneRepository.existsByCode(droneDTO.getCode())) {
            throw new KeyRepeatedDataExeception("Ya existe otro dron con el código ingresado: " + droneDTO.getCode());
        }

        if (droneDTO.getSerialNumber() == null || droneDTO.getSerialNumber().isBlank()){
            throw new IncompleteDataException("El número de serie es obligatorio");
        }

        if (!drone.getSerialNumber().equals(droneDTO.getSerialNumber()) &&
                droneRepository.existsBySerialNumber(droneDTO.getSerialNumber())) {
            throw new KeyRepeatedDataExeception("Ya existe otro dron con el número de serie: " + droneDTO.getSerialNumber());
        }

        drone.setCode(droneDTO.getCode());
        drone.setSerialNumber(droneDTO.getSerialNumber());

        // Validamos y corregimos el modelo solo si enviaron uno distinto
        if (droneDTO.getDroneModelId() != null && !drone.getDroneModel().getId().equals(droneDTO.getDroneModelId())) {

            DroneModel model = droneModelRepository.findById(droneDTO.getDroneModelId())
                    .orElseThrow(() -> new ResourceNotFoundException("Modelo no encontrado"));
            drone.setDroneModel(model);
        }


        droneRepository.save(drone);

        return droneDTO;
    }

    @Override
    public void delete(Long id) {
        if (!droneRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar: el Drone con ID " + id + " no existe.");
        }
        droneRepository.deleteById(id);
    }

    @Override
    public Drone listId(Long id) {
        return  droneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el Drone con el ID:" +id));
    }

    @Override
    public List<DroneDTO> getAvailableDronesByDates(LocalDate startDate, LocalDate endDate) {
        //llamado a la consulta personalizada del repo
        List<Drone> drones = droneRepository.findAvailableDronesByDates(startDate, endDate);

        return drones.stream().map(drone -> new DroneDTO(
                String.valueOf(drone.getId()),
                drone.getAcquisitionDate(),
                drone.getSerialNumber(),
                drone.getDroneModel() != null ? drone.getDroneModel().getId() : null ,
                drone.getDroneModel() != null ? drone.getDroneModel().getModelName() : "Sin modelo"
        )).collect(Collectors.toList());
    }

    @Override
    public void logicDelete(Long id) {
        Drone drone = droneRepository.findById(id).
                orElseThrow(() ->
                        new ResourceNotFoundException("El dron con ID " + id + " no existe."));

        drone.setIsActive(false);
        droneRepository.save(drone);
    }

}
package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.DroneAvailableDTO;
import com.arqui.seedair.dtos.DroneDTO;
import com.arqui.seedair.entities.Drone;
import com.arqui.seedair.entities.DroneModel;
import com.arqui.seedair.exceptions.KeyRepeatedDataExeception;
import com.arqui.seedair.exceptions.ResourceNotFoundException;
import com.arqui.seedair.repositories.DroneModelRepository;
import com.arqui.seedair.repositories.DroneRepository;
import com.arqui.seedair.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
                "Activo",
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
    public List<Drone> getDronesByStatus(Boolean isActive) {
        return droneRepository.findByCurrentStatus(isActive);
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
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el Drone con el ID: + id"));
    }


}
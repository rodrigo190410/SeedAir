package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.DroneAvailableDTO;
import com.arqui.seedair.dtos.DroneDTO;
import com.arqui.seedair.entities.Drone;
import com.arqui.seedair.entities.DroneModel;
import com.arqui.seedair.repositories.DroneModelRepository;
import com.arqui.seedair.repositories.DroneRepository;
import com.arqui.seedair.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
    public Drone addDTO(DroneDTO dto) {
        // 1. Buscamos el modelo que indicaste en el DTO
        DroneModel model = droneModelRepository.findById(dto.getDroneModelId())
                .orElseThrow(() -> new RuntimeException("Modelo no encontrado"));
        // 2. Creamos la entidad Drone y mapeamos
        Drone drone = new Drone();
        drone.setSerialNumber(dto.getSerialNumber());
        drone.setDroneModel(model); // Aquí se hace la relación
        drone.setCurrentStatus("ACTIVE"); // Estado inicial por defecto
        return droneRepository.save(drone);
    }

    @Override
    public Drone findById(Long id) {
        return droneRepository.findById(id).orElse(null);
    }

    @Override
    public List<Drone> getDronesByStatus(String status) {
        return droneRepository.findByCurrentStatus(status);
    }

    @Override
    public void delete(Long id) {
        if (!droneRepository.existsById(id)) {
            throw new NoSuchElementException("No se encontro el drone con ese id:" + id);
        }
        droneRepository.deleteById(id);
    }


}

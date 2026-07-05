package com.arqui.seedair.services;

import com.arqui.seedair.dtos.DroneAvailableDTO;
import com.arqui.seedair.dtos.DroneDTO;
import com.arqui.seedair.dtos.DroneDTOUpdate;
import com.arqui.seedair.dtos.DroneListDTO;
import com.arqui.seedair.entities.Drone;

import java.util.List;

public interface DroneService {

    public Drone addDrone(Drone drone);
    //public DroneAvailableDTO addDroneAvailableDTO(DroneAvailableDTO droneAvailableDTO);
    public List<DroneAvailableDTO> getAvailableDrones();
    public DroneDTO addDTO(DroneDTO dto);
    public Drone findById(Long id);
    public List<DroneListDTO> getDronesByIsActive(Boolean isActive);
    public DroneDTOUpdate update(DroneDTOUpdate droneDTO);
    void delete(Long id);
    void logicDelete(Long id);
    public Drone listId(Long id);
}

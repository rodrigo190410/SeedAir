package com.arqui.seedair.services;

import com.arqui.seedair.dtos.DroneAvailableDTO;
import com.arqui.seedair.dtos.DroneDTO;
import com.arqui.seedair.entities.Drone;

import java.util.List;

public interface DroneService {

    public Drone addDrone(Drone drone);
    //public DroneAvailableDTO addDroneAvailableDTO(DroneAvailableDTO droneAvailableDTO);
    public List<DroneAvailableDTO> getAvailableDrones();
    public DroneDTO addDTO(DroneDTO dto);
    public Drone findById(Long id);
    public List<Drone> getDronesByStatus(String status);
    void delete(Long id);
}

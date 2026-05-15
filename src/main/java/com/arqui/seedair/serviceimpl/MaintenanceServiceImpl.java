package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.MaintenanceRegisterDTO;
import com.arqui.seedair.entities.Drone;
import com.arqui.seedair.entities.Maintenance;
import com.arqui.seedair.repositories.MaintenanceRepository;
import com.arqui.seedair.services.DroneService;
import com.arqui.seedair.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {
    @Autowired
    MaintenanceRepository maintenanceRepository;
    @Autowired
    DroneService droneService;

    @Override
    public Maintenance add(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public MaintenanceRegisterDTO register(MaintenanceRegisterDTO maintenanceRegister) {
        Drone drone = droneService.findById(maintenanceRegister.getDroneId());
        Maintenance newMaintenance = new Maintenance(
                null, maintenanceRegister.getStartDate(),null,
                maintenanceRegister.getStatus(),maintenanceRegister.getDescription(),
                maintenanceRegister.getCost(), drone
        );
        maintenanceRepository.save(newMaintenance);
        return maintenanceRegister;
    }
}

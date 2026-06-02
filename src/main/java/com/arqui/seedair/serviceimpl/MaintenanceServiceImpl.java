package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.MaintenanceEndDateDTO;
import com.arqui.seedair.dtos.MaintenanceRegisterDTO;
import com.arqui.seedair.entities.Drone;
import com.arqui.seedair.entities.Maintenance;
import com.arqui.seedair.exceptions.IncompleteDataException;
import com.arqui.seedair.exceptions.InvalidDataRangeException;
import com.arqui.seedair.exceptions.KeyRepeatedDataExeception;
import com.arqui.seedair.exceptions.ResourceNotFoundException;
import com.arqui.seedair.repositories.MaintenanceRepository;
import com.arqui.seedair.services.DroneService;
import com.arqui.seedair.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        if (maintenanceRegister.getStartDate() == null || maintenanceRegister.getStartDate().isBefore(LocalDate.now())) {
            throw new InvalidDataRangeException("La fecha de inicio es inválida.");
        }

        if (maintenanceRegister.getCost() == null || maintenanceRegister.getCost() < 0.0) {
            throw new InvalidDataRangeException("El costo no puede ser negativo.");
        }

        if (maintenanceRegister.getDescription() == null || maintenanceRegister.getDescription().isBlank()) {
            throw new IncompleteDataException("La descripción es obligatoria.");
        }
        Boolean exists = maintenanceRepository.existsByDroneIdAndStartDate(maintenanceRegister.getDroneId(), maintenanceRegister.getStartDate());
        if (exists){
            throw new KeyRepeatedDataExeception("El dron ya tiene un mantenimiento registrado en esta fecha");
        }
        Boolean inMaintenance = maintenanceRepository.existsByDroneIdAndIsFinishedFalse(maintenanceRegister.getDroneId());
        if (inMaintenance){
            throw new InvalidDataRangeException("El dron aún está en mantenimiento");
        }
        Drone drone = droneService.findById(maintenanceRegister.getDroneId());
        Maintenance newMaintenance = new Maintenance(
                null, maintenanceRegister.getStartDate(),null,
                false,maintenanceRegister.getDescription(),
                maintenanceRegister.getCost(), drone
        );
        maintenanceRepository.save(newMaintenance);
        return maintenanceRegister;
    }

    @Override
    public Maintenance findById(Long id) {
        return maintenanceRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("El maintenance con ID " + id + " no existe."));
    }

    @Override
    public MaintenanceEndDateDTO registerEndDate(MaintenanceEndDateDTO maintenanceEndDate) {

        Maintenance foundMaintenance = findById(maintenanceEndDate.getId());
        foundMaintenance.setEndDate(maintenanceEndDate.getMaintenanceEndDate());

        maintenanceRepository.save(foundMaintenance);
        return maintenanceEndDate;
    }

    @Override
    public List<Maintenance> listAll() {
        return maintenanceRepository.findAll();
    }
}

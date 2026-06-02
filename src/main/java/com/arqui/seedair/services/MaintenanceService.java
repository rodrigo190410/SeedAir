package com.arqui.seedair.services;

import com.arqui.seedair.dtos.MaintenanceEndDateDTO;
import com.arqui.seedair.dtos.MaintenanceRegisterDTO;
import com.arqui.seedair.entities.Maintenance;

import java.util.List;

public interface MaintenanceService {
    public Maintenance add(Maintenance maintenance);
    public MaintenanceRegisterDTO register(MaintenanceRegisterDTO maintenanceRegister);
    public Maintenance findById(Long id);
    public MaintenanceEndDateDTO registerEndDate(MaintenanceEndDateDTO maintenanceEndDate);
    public List<Maintenance> listAll();
}

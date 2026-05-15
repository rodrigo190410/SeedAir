package com.arqui.seedair.services;

import com.arqui.seedair.dtos.MaintenanceRegisterDTO;
import com.arqui.seedair.entities.Maintenance;

public interface MaintenanceService {
    public Maintenance add(Maintenance maintenance);
    public MaintenanceRegisterDTO register(MaintenanceRegisterDTO maintenanceRegister);
}

package com.arqui.seedair.controllers;

import com.arqui.seedair.dtos.MaintenanceRegisterDTO;
import com.arqui.seedair.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/seedair") // http://localhost:8080/seedair
public class MaintenanceController {
    @Autowired
    MaintenanceService maintenanceService;

    @PostMapping("/maintenances/register") // http://localhost:8080/seedair/maintenances/register
    public ResponseEntity<MaintenanceRegisterDTO> register(@RequestBody MaintenanceRegisterDTO maintenanceRegister){
        MaintenanceRegisterDTO newMaintenance = maintenanceService.register(maintenanceRegister);
        return new ResponseEntity<>(newMaintenance, HttpStatus.CREATED);
    }
}

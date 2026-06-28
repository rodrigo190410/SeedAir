package com.arqui.seedair.controllers;

import com.arqui.seedair.dtos.MaintenanceEndDateDTO;
import com.arqui.seedair.dtos.MaintenanceRegisterDTO;
import com.arqui.seedair.dtos.MaintenanceResponseDTO;
import com.arqui.seedair.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/maintenances/update/enddate") // http://localhost:8080/seedair/maintenances/set/enddate
    public ResponseEntity<MaintenanceEndDateDTO> updateEndDate(@RequestBody MaintenanceEndDateDTO maintenanceEndDateDTO){
        MaintenanceEndDateDTO endDate = maintenanceService.registerEndDate(maintenanceEndDateDTO);
        return new ResponseEntity<>(endDate, HttpStatus.OK);
    }

    @GetMapping("/maintenances") // http://localhost:8080/seedair/maintenances/set/enddate
    public ResponseEntity<List<MaintenanceResponseDTO>> listMaintenances(){
        List<MaintenanceResponseDTO> dtoList = maintenanceService.listMaintenances();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @DeleteMapping("/maintenances/{id}") // http://localhost:8080/seedair/maintenances/set/enddate
    public ResponseEntity<HttpStatus> delete(Long id){
        maintenanceService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

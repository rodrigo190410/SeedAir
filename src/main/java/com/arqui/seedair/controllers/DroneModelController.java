package com.arqui.seedair.controllers;


import com.arqui.seedair.dtos.DroneModelDTO;
import com.arqui.seedair.services.DroneModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/seedair/droneModel") // http://localhost:8080/seedair/droneModel
public class DroneModelController {

    @Autowired
    DroneModelService droneModelService;

    @PostMapping("/add") // http://localhost:8080/seedair/droneModel/add
    public ResponseEntity<DroneModelDTO> addDTO(@RequestBody DroneModelDTO droneModel) {
        return new ResponseEntity<>(droneModelService.addDTO(droneModel), HttpStatus.CREATED);
    }


}

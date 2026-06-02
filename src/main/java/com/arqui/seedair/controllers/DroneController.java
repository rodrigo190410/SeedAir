package com.arqui.seedair.controllers;

import com.arqui.seedair.dtos.DroneAvailableDTO;
import com.arqui.seedair.dtos.DroneDTO;
import com.arqui.seedair.entities.Drone;
import com.arqui.seedair.services.DroneService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin("*")
@RequestMapping("/seedair") // http://localhost:8080/seedair
public class DroneController {

    @Autowired
    DroneService droneService;

    @GetMapping("/drones/available") // http://localhost:8080/seedair/drones/available
    public ResponseEntity<List<DroneAvailableDTO>> getAvailableDrones() {
        List<DroneAvailableDTO> drones = droneService.getAvailableDrones();
        return new ResponseEntity<>(drones, HttpStatus.OK);
    }

    @GetMapping("/drones/status/{isActive}") // http://localhost:8080/seedair/drones/status/MAINTENANCE o INACTIVE
    public ResponseEntity<List<Drone>> getDroneByStatus(@PathVariable Boolean isActive) {
        List<Drone> drones = droneService.getDronesByStatus(isActive);
        return new ResponseEntity<>(drones, HttpStatus.OK);
    }

    @GetMapping("/drones/{id}") // http://localhost:8080/seedair/drones/{id}
    public ResponseEntity<DroneDTO> getById(@PathVariable Long id){
        Drone drone = droneService.listId(id);
        ModelMapper m = new ModelMapper();
        DroneDTO dto= m.map(drone, DroneDTO.class);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/drones/add")// http://localhost:8080/seedair/drones/add
    public ResponseEntity<DroneDTO> addDrone(@RequestBody DroneDTO drone) {
        return new ResponseEntity<>(droneService.addDTO(drone), HttpStatus.CREATED);
    }

    @DeleteMapping("/drones/delete/{id}") //http://localhost:8080/seedair/drones/delete/6
    public ResponseEntity<Void> deleteDrone(@PathVariable Long id) {
        droneService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

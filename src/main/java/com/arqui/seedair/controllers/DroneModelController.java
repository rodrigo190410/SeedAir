package com.arqui.seedair.controllers;


import com.arqui.seedair.dtos.DroneModelListDTO;
import com.arqui.seedair.dtos.DroneModelDTO;
import com.arqui.seedair.entities.Drone;
import com.arqui.seedair.entities.DroneModel;
import com.arqui.seedair.services.DroneModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin("*")
@RequestMapping("/seedair") // http://localhost:8080/seedair
public class DroneModelController {

    @Autowired
    DroneModelService droneModelService;

    @PostMapping("/droneModels/add") // http://localhost:8080/seedair/droneModel/add
    public ResponseEntity<DroneModelDTO> addDTO(@RequestBody DroneModelDTO droneModel) {
        return new ResponseEntity<>(droneModelService.addDTO(droneModel), HttpStatus.CREATED);
    }

    @DeleteMapping("/droneModels/delete/{id}") //http://localhost:8080/seedair/droneModel/delete/6
    public ResponseEntity<Drone> deleteDrone(@PathVariable Long id) {
        try{
            droneModelService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/droneModels")
    public ResponseEntity<List<DroneModelListDTO>> listDroneModels(){
        List<DroneModelListDTO> listModels = droneModelService.listDroneModels();
        return new ResponseEntity<>(listModels, HttpStatus.OK);
    }

    @GetMapping("/droneModels/{droneModelId}")
    public ResponseEntity<DroneModelDTO> listDroneModels(@PathVariable Long droneModelId){
        DroneModelDTO listModels = droneModelService.getDroneModelById(droneModelId);
        return new ResponseEntity<>(listModels, HttpStatus.OK);
    }

    @PutMapping("/droneModels/update")
    public ResponseEntity<DroneModelDTO> update(@RequestBody DroneModelDTO droneModelDTO) {

        DroneModelDTO updatedModel = droneModelService.update(droneModelDTO);

        return new ResponseEntity<>(updatedModel, HttpStatus.OK);
    }
    // Eliminación lógica
    @DeleteMapping("/droneModels/logicalDelete/{droneModelId}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long droneModelId) {
        droneModelService.logicDelete(droneModelId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/droneModels/isActive/{isActive}") // http://localhost:8080/seedair/droneModels/isActive/true
    public ResponseEntity<List<DroneModelListDTO>> listDroneModelsByIsActive(@PathVariable Boolean isActive) {

        List<DroneModelListDTO> droneModels = droneModelService.getDroneModelsByIsActive(isActive);

        return new ResponseEntity<>(droneModels, HttpStatus.OK);
    }

}

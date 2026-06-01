package com.arqui.seedair.controllers;

import com.arqui.seedair.dtos.DroneBrandRegisterDTO;
import com.arqui.seedair.services.DroneBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/seedair") // http://localhost:8080/seedair
public class DroneBrandController {
    @Autowired
    DroneBrandService droneBrandService;
    @PostMapping("/brands/register") // http://localhost:8080/seedair/brands/register
    public ResponseEntity<DroneBrandRegisterDTO> register(@RequestBody DroneBrandRegisterDTO droneBrandRegisterDTO){
        DroneBrandRegisterDTO droneBrand = droneBrandService.register(droneBrandRegisterDTO);
        return new ResponseEntity<>(droneBrand, HttpStatus.CREATED);
    }
}

package com.arqui.seedair.controllers;

import com.arqui.seedair.dtos.ParcelDTO;
import com.arqui.seedair.services.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequestMapping("/seedair")
public class ParcelController {
    @Autowired
    ParcelService parcelService;

    //registro nueva parcela ->para el cliente
    @PostMapping("/parcels/register") // http://localhost:8080/seedair/parcels/register
    public ResponseEntity<ParcelDTO> register(@RequestBody ParcelDTO parcelDTO, Long customerId){
        ParcelDTO newParcel = parcelService.register(parcelDTO);
        return new ResponseEntity<>(newParcel, HttpStatus.CREATED);
    }
}

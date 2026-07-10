package com.arqui.seedair.controllers;

import com.arqui.seedair.dtos.ParcelDTO;
import com.arqui.seedair.dtos.ParcelDTOByCustomerId;
import com.arqui.seedair.dtos.ParcelResponseDTO;
import com.arqui.seedair.services.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/seedair")
public class ParcelController {
    @Autowired
    ParcelService parcelService;

    //Registro nueva parcela -> para el cliente
    @PostMapping("/parcels/register") // http://localhost:8080/seedair/parcels/register
    public ResponseEntity<ParcelDTO> register(@RequestBody ParcelDTO parcelDTO){
        ParcelDTO newParcel = parcelService.register(parcelDTO);
        return new ResponseEntity<>(newParcel, HttpStatus.CREATED);
    }

    //obtener listado de todas las parcelas
    @GetMapping("/parcels") // http://localhost:8080/seedair/parcels
    public ResponseEntity<List<ParcelResponseDTO>> listParcels(){
        List<ParcelResponseDTO> parcelsList = parcelService.listParcels();
        return new ResponseEntity<>(parcelsList, HttpStatus.OK);
    }

    //obtener lisstado de parcelas para admin
    @GetMapping("/parcels/list/{customerId}")
    public ResponseEntity<List<ParcelDTOByCustomerId>> listParcelsByCustomerId(@PathVariable Long customerId){
        List<ParcelDTOByCustomerId> customerParcelsList = parcelService.listParcelDTOById(customerId);
        return new ResponseEntity<>(customerParcelsList, HttpStatus.OK);
    }
    //obtener lisstado de parcelas para el customer
    @GetMapping("/parcels/list/customerParcels")
    public ResponseEntity<List<ParcelDTOByCustomerId>> listParcelsByCustomer(){
        List<ParcelDTOByCustomerId> customerParcelsList = parcelService.listParcelDTOByCustomer();
        return new ResponseEntity<>(customerParcelsList, HttpStatus.OK);
    }


    //Actualizar parcela
    @PutMapping("/parcels/update")
    public ResponseEntity<ParcelDTOByCustomerId> update(@RequestBody ParcelDTOByCustomerId parcelDTO){
        ParcelDTOByCustomerId updatedParcel = parcelService.update(parcelDTO);
        return new ResponseEntity<>(updatedParcel, HttpStatus.OK);
    }

    //Obtener parcela by iD
    @GetMapping("/parcels/getById/{parcelId}")
    public ResponseEntity<ParcelDTOByCustomerId> getParcelById(@PathVariable Long parcelId){
        ParcelDTOByCustomerId parcelById= parcelService.findParcelById(parcelId);
        return new ResponseEntity<>(parcelById, HttpStatus.OK);
    }
}

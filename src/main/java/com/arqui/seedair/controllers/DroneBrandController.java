package com.arqui.seedair.controllers;

import com.arqui.seedair.dtos.DroneBrandRegisterDTO;
import com.arqui.seedair.dtos.DroneBrandResponseDTO;
import com.arqui.seedair.entities.DroneBrand;
import com.arqui.seedair.services.DroneBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/brands") // http://localhost:8080/seedair/brands
    public ResponseEntity<List<DroneBrandResponseDTO>> listBrands(){
        List<DroneBrandResponseDTO> list = droneBrandService.listDroneBrands();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @GetMapping("/brands/{brandId}") // http://localhost:8080/seedair/brands
    public ResponseEntity<DroneBrand> findById(@PathVariable("brandId") Long id){
        DroneBrand foundBrand = droneBrandService.findById(id);
        return new ResponseEntity<>(foundBrand, HttpStatus.OK);
    }
    @PutMapping("/brands/update")
    public ResponseEntity<DroneBrand> update(@RequestBody DroneBrand droneBrand){
        DroneBrand updatedBrand = droneBrandService.update(droneBrand);
        return new ResponseEntity<>(updatedBrand, HttpStatus.OK);
    }
    @DeleteMapping("/brands/{brandId}") // http://localhost:8080/seedair/brands
    public ResponseEntity<HttpStatus> delete(@PathVariable("brandId") Long id){
        droneBrandService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

package com.arqui.seedair.services;

import com.arqui.seedair.dtos.DroneBrandRegisterDTO;
import com.arqui.seedair.dtos.DroneBrandResponseDTO;
import com.arqui.seedair.entities.DroneBrand;

import java.util.List;

public interface DroneBrandService {
    public DroneBrand add(DroneBrand droneBrand);
    public DroneBrandRegisterDTO register(DroneBrandRegisterDTO droneBrandDTO);
    public DroneBrand findById(Long id);
    public List<DroneBrand> listAll();
    public List<DroneBrandResponseDTO> listDroneBrands();
    public DroneBrand update(DroneBrand droneBrand);
    public void delete(Long id);
}

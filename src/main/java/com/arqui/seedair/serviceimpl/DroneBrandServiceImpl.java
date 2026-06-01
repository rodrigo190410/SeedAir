package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.DroneBrandRegisterDTO;
import com.arqui.seedair.entities.DroneBrand;
import com.arqui.seedair.exceptions.IncompleteDataException;
import com.arqui.seedair.exceptions.KeyRepeatedDataExeception;
import com.arqui.seedair.repositories.DroneBrandRepository;
import com.arqui.seedair.services.DroneBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneBrandServiceImpl implements DroneBrandService {
    @Autowired
    DroneBrandRepository droneBrandRepository;
    @Override
    public DroneBrand add(DroneBrand droneBrand) {
        return droneBrandRepository.save(droneBrand);
    }

    @Override
    public DroneBrandRegisterDTO register(DroneBrandRegisterDTO droneBrandDTO) {
        List<DroneBrand> droneBrandList = listAll();
        if (droneBrandDTO.getBrandName()==null || droneBrandDTO.getBrandName().isBlank()){
            throw new IncompleteDataException("Debe ingresar el nombre de alguna marca");
        }
        for (DroneBrand d: droneBrandList){
            if (droneBrandDTO.getBrandName().equals(d.getName())){
                throw new KeyRepeatedDataExeception("La marca: "+ droneBrandDTO.getBrandName() + " ya está registrada");
            }
        }

        DroneBrand newBrand = new DroneBrand(
                null,
                droneBrandDTO.getBrandName(),
                null
        );
        droneBrandRepository.save(newBrand);

        return droneBrandDTO;
    }

    @Override
    public DroneBrand findById(Long id) {
        return droneBrandRepository.findById(id).orElse(null);
    }

    @Override
    public List<DroneBrand> listAll() {
        return droneBrandRepository.findAll();
    }
}

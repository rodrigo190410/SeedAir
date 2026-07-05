package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.DroneBrandRegisterDTO;
import com.arqui.seedair.dtos.DroneBrandResponseDTO;
import com.arqui.seedair.entities.DroneBrand;
import com.arqui.seedair.exceptions.IncompleteDataException;
import com.arqui.seedair.exceptions.KeyRepeatedDataExeception;
import com.arqui.seedair.exceptions.ResourceNotFoundException;
import com.arqui.seedair.repositories.DroneBrandRepository;
import com.arqui.seedair.repositories.DroneModelRepository;
import com.arqui.seedair.services.DroneBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DroneBrandServiceImpl implements DroneBrandService {
    @Autowired
    DroneBrandRepository droneBrandRepository;
    @Autowired
    DroneModelRepository droneModelRepository;

    @Override
    public DroneBrand add(DroneBrand droneBrand) {
        return droneBrandRepository.save(droneBrand);
    }


    @Override
    public DroneBrandRegisterDTO register(DroneBrandRegisterDTO droneBrandDTO) {
        List<DroneBrand> droneBrandList = listAll();
        if (droneBrandDTO.getName()==null || droneBrandDTO.getName().isBlank()){
            throw new IncompleteDataException("Debe ingresar el nombre de alguna marca");
        }
        for (DroneBrand d: droneBrandList){
            if (droneBrandDTO.getName().equals(d.getName())){
                throw new KeyRepeatedDataExeception("La marca: "+ droneBrandDTO.getName() + " ya está registrada");
            }
        }

        DroneBrand newBrand = new DroneBrand(
                null,
                droneBrandDTO.getName(),
                null
        );
        droneBrandRepository.save(newBrand);

        return droneBrandDTO;
    }

    @Override
    public DroneBrand findById(Long id) {
        return droneBrandRepository.findById(id).
                orElseThrow(()->new ResourceNotFoundException("El dron con el id: "+ id + " no fue encontrado"));
    }

    @Override
    public List<DroneBrand> listAll() {
        return droneBrandRepository.findAll();
    }

    @Override
    public List<DroneBrandResponseDTO> listDroneBrands() {
        List<DroneBrand> list = listAll();
        List<DroneBrandResponseDTO> dtoList = new ArrayList<>();
        for (DroneBrand d:list){
            DroneBrandResponseDTO dto = new DroneBrandResponseDTO(d.getId(), d.getName());
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public DroneBrand update(DroneBrand droneBrand) {
        DroneBrand foundBrand = findById(droneBrand.getId());
        foundBrand.setName(droneBrand.getName());
        droneBrandRepository.save(foundBrand);
        return droneBrand;
    }

    @Override
    public void delete(Long id) {

        DroneBrand brand = droneBrandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La marca no existe."));

        if (droneModelRepository.existsByDroneBrandId(id)) {
            throw new IllegalStateException("No es posible eliminar esta marca porque ya tiene drones registrados con su Id.");
        }

        droneBrandRepository.deleteById(id);
    }
}

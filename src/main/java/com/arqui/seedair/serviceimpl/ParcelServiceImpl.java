package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.ParcelDTO;
import com.arqui.seedair.entities.Customer;
import com.arqui.seedair.entities.Parcel;
import com.arqui.seedair.repositories.ParcelRepository;
import com.arqui.seedair.services.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public class ParcelServiceImpl implements ParcelService{
    @Autowired
    ParcelRepository parcelRepository;
    //@Autowired
    //CustomerRepository customerRepository;
    @Override
    public Parcel add(Parcel parcel) {
        return parcelRepository.save(parcel);
    }

    @Override
    public Parcel findById(Long id) {
        return parcelRepository.findById(id).get();
    }

    //Eliminar para reemplazarlo por el que se encuentra debajo
    @Override
    public ParcelDTO register(ParcelDTO parcelDTO) {
        return null;
    }
/*
    @Override
    public ParcelDTO register(ParcelDTO parcelDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Customer customer = customerRepository.findByUser_username(username);

        Parcel newParcel = new Parcel(null,
                parcelDTO.getLocationText(), parcelDTO.getTotalHectares(),
                parcelDTO.getLatitude(), parcelDTO.getLongitude(), LocalDate.now(),
                null, customer
        );
        parcelRepository.save(newParcel);
        return parcelDTO;
    }
    */
}

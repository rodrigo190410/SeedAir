package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.ParcelDTO;
import com.arqui.seedair.entities.Customer;
import com.arqui.seedair.entities.Parcel;
import com.arqui.seedair.exceptions.IncompleteDataException;
import com.arqui.seedair.exceptions.InvalidDataRangeException;
import com.arqui.seedair.exceptions.KeyRepeatedDataExeception;
import com.arqui.seedair.exceptions.ResourceNotFoundException;
import com.arqui.seedair.repositories.CustomerRepository;
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
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public Parcel add(Parcel parcel) {
        return parcelRepository.save(parcel);
    }

    @Override
    public Parcel findById(Long id) {
        return parcelRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("La parcela con ID " + id + " no existe."));
    }



    @Override
    public ParcelDTO register(ParcelDTO parcelDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Customer customer = customerRepository.findByUser_username(username);
        if (parcelDTO.getLocationText().isBlank()){
            throw new IncompleteDataException("El nombre de la ubicación es obligatoria");
        }
        if (parcelDTO.getTotalHectares() < 0){
            throw new InvalidDataRangeException("El total de hectáreas no puede ser negativa");
        }
        if (parcelDTO.getLatitude() == null){
            throw new IncompleteDataException("La latitud es obligatoria");
        }
        if (parcelDTO.getLongitude() == null){
            throw new IncompleteDataException("La longitud es obligatoria");
        }
        if (parcelDTO.getLatitude() < -90 || parcelDTO.getLatitude() > 90) {
            throw new InvalidDataRangeException("La latitud debe estar entre -90 y 90");
        }
        if (parcelDTO.getLongitude() < -180 || parcelDTO.getLongitude() > 180) {
            throw new InvalidDataRangeException("La longitud debe estar entre -180 y 180");
        }

        Boolean exists = parcelRepository.existsByLatitudeAndLongitude(parcelDTO.getLatitude(), parcelDTO.getLongitude());
        if (exists){
            throw new KeyRepeatedDataExeception("La parcela ya está registrada");
        }
        Parcel newParcel = new Parcel(null,
                parcelDTO.getLocationText(), parcelDTO.getTotalHectares(),
                parcelDTO.getLatitude(), parcelDTO.getLongitude(), LocalDate.now(),
                null, customer
        );
        parcelRepository.save(newParcel);
        return parcelDTO;
    }

}

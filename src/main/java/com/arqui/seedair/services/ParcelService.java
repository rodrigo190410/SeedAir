package com.arqui.seedair.services;

import com.arqui.seedair.dtos.ParcelDTO;
import com.arqui.seedair.entities.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelService {
    public Parcel add(Parcel parcel);
    public Parcel findById(Long id);
    public ParcelDTO register(ParcelDTO parcelDTO);
}

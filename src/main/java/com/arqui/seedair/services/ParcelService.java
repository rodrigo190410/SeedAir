package com.arqui.seedair.services;

import com.arqui.seedair.dtos.ParcelDTO;
import com.arqui.seedair.dtos.ParcelDTOByCustomerId;
import com.arqui.seedair.entities.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParcelService {
    public Parcel add(Parcel parcel);
    public Parcel findById(Long id);
    public ParcelDTO register(ParcelDTO parcelDTO);
    public List<ParcelDTOByCustomerId> listParcelDTOById(Long id);

    void logicDelete(Long id);

    public ParcelDTOByCustomerId findParcelById(Long id);

    public ParcelDTOByCustomerId update(ParcelDTOByCustomerId parcelDTO);
}

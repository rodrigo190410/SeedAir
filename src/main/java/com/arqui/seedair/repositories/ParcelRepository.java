package com.arqui.seedair.repositories;

import com.arqui.seedair.dtos.ParcelDTOByCustomerId;
import com.arqui.seedair.entities.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    Boolean existsByLatitudeAndLongitude(Double latitude, Double longitude);
    /////
    List<Parcel> findByCustomerId(Long customerId);

    public ParcelDTOByCustomerId findParcelById(Long parcelId);


}

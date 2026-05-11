package com.arqui.seedair.repositories;

import com.arqui.seedair.entities.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
}

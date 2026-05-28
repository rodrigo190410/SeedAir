package com.arqui.seedair.repositories;

import com.arqui.seedair.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    //QUERY METHOD
    List<Reservation> findByScheduledStartDateBetween(LocalDate startFilter, LocalDate endFilter);
    List<Reservation> findByIsActive(Boolean isActive);
    //JPQL
    @Query(value = "SELECT r FROM Reservation r WHERE r.customer.firstName=?1", nativeQuery = false)
    List<Reservation> findByCustomerName(String name);


    //JPQL CHEKEAR
    @Query("SELECT r FROM Reservation r WHERE r.customer.user.username = :username AND r.isActive = true")
    List<Reservation> findReservationByUsername(@Param("username") String username);
}

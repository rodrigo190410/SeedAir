package com.arqui.seedair.repositories;

import com.arqui.seedair.dtos.CustomerSummaryDTO;
import com.arqui.seedair.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //QUERY METHOD
    public Customer findByUser_username(String username);
    public Customer findByUser_id(Long id);

    //SQL NATIVE
    @Query(value = "SELECT DISTINCT c.first_name as firstName, c.last_name as lastName, c.phone as phone " +
            "FROM customers c " +
            "INNER JOIN parcels p ON c.id = p.customer_id " +
            "LEFT JOIN reservations r ON p.id = r.parcel_id " +
            "WHERE r.id IS NULL", nativeQuery = true)
    List<CustomerSummaryDTO> findCustomersWithParcelButNoReservation();


    @Query(value = "SELECT c.first_name as firstName, c.last_name as lastName, c.phone as phone " +
            "FROM customers c " +
            "INNER JOIN parcels p ON c.id = p.customer_id " +
            "GROUP BY c.id, c.first_name, c.last_name, c.phone " +
            "HAVING COUNT(p.id) > :cantidad", nativeQuery = true)
    List<CustomerSummaryDTO> findCustomersWithMoreThanXParcels(@Param("cantidad") Integer cantidad);


    @Query(value = "SELECT DISTINCT c.first_name as firstName, c.last_name as lastName, c.phone as phone " +
            "FROM customers c " +
            "INNER JOIN reservations r ON c.id = r.customer_id " +
            "WHERE c.id NOT IN (SELECT customer_id FROM reviews)", nativeQuery = true)
    List<CustomerSummaryDTO> findCustomersWithReservationButNoReview();


    @Query(value = "SELECT DISTINCT c.first_name as firstName, c.last_name as lastName, c.phone as phone " +
            "FROM customers c " +
            "INNER JOIN reviews r ON c.id = r.customer_id " +
            "WHERE r.is_visible = true AND r.rating > :ratingValue", nativeQuery = true)
    List<CustomerSummaryDTO> findCustomersWithVisibleReviewAndRatingGreaterThan(@Param("ratingValue") Double ratingValue);
}


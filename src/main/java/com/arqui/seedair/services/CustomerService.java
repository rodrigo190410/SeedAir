package com.arqui.seedair.services;

import com.arqui.seedair.dtos.CustomerDTO;
import com.arqui.seedair.dtos.CustomerSummaryDTO;
import com.arqui.seedair.entities.Customer;

import java.util.List;

public interface CustomerService {
    public Customer add(Customer customer);
    public List<Customer> listAll();
    public Customer findById(Long id);
    public CustomerDTO addDTO(CustomerDTO customerDTO);

    List<CustomerSummaryDTO> getCustomersNoReservation();
    List<CustomerSummaryDTO> getCustomersMoreThanParcels(Integer cantidad);
    List<CustomerSummaryDTO> getCustomersWithReservationNoReviews();
    List<CustomerSummaryDTO> getCustomersHighRating(Double rating);
}
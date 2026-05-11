package com.arqui.seedair.controllers;

import com.arqui.seedair.dtos.CustomerDTO;
import com.arqui.seedair.dtos.CustomerSummaryDTO;
import com.arqui.seedair.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/seedair") // http://localhost:8080/seedair
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("users/register/customer") // http://localhost:8080/seedair/users/register/customer
    public ResponseEntity<CustomerDTO> register(@RequestBody CustomerDTO customer){
      CustomerDTO cust = customerService.addDTO(customer);
      return new ResponseEntity<>(cust, HttpStatus.CREATED);
    };
    //Seleccionar los clientes que tienen al menos un terreno que nunca ha tenido un reservación
    @GetMapping("/customers/atleastoneparcelandnoreservations")//http://localhost:8080/seedair/customers/query1
    public List<CustomerSummaryDTO> getCustomersNoReservation() {
        return customerService.getCustomersNoReservation();
    }

    //Seleccionar los clientes que tienen más de una cantidad específica de parcelas
    @GetMapping("/customers/morethanparcels/{cantidad}")//http://localhost:8080/seedair/customers/query2/{cantidad}
    public List<CustomerSummaryDTO> getCustomersMoreThanParcels(@PathVariable Integer cantidad) {
        return customerService.getCustomersMoreThanParcels(cantidad);
    }

    //Seleccionar a los clientes que tienen al menos una reserva pero que nunca han escrito una reseña
    @GetMapping("/customers/withreservationnoreviews") //http://localhost:8080/seedair/customers/query3
    public List<CustomerSummaryDTO> getCustomersWithReservationNoReviews() {
        return customerService.getCustomersWithReservationNoReviews();
    }

    //Seleccionar los clientes que tienen al menos una reseña visible con una calificacion superior a un valor específico
    @GetMapping("/customers/visiblereviewshigherthan/{rating}")//http://localhost:8080/seedair/customers/query4/{rating}
    public List<CustomerSummaryDTO> getCustomersHighRating(@PathVariable Double rating) {
        return customerService.getCustomersHighRating(rating);
    }
}

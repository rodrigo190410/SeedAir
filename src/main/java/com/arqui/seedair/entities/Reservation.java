package com.arqui.seedair.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate scheduledStartDate;
    private LocalDate scheduledEndDate;
    private Double hectares;
    private Double ratePerHectare;
    private Double totalAmount;
    private String status;
    //->review
    @JsonIgnore
    @OneToOne(mappedBy = "reservation")
    private Review review;
    //->payment
    @JsonIgnore
    @OneToMany(mappedBy = "reservation", fetch = FetchType.EAGER)
    private List<Payment> payments;
    //<-customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    //<-parcel
    @ManyToOne
    @JoinColumn(name="parcel_id")
    private Parcel parcel;
    //<-operator
    @ManyToOne
    @JoinColumn(name = "operator_id")
    private Operator operator;
    //<-drone
    @ManyToOne
    @JoinColumn(name = "drone_id")
    private Drone drone;
}

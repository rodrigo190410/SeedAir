package com.arqui.seedair.entities;

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
@Table(name = "parcels")
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String locationText;
    private Double areaHectares;
    private Double latitude;
    private Double longitude;
    private LocalDate createdAt;
    //->reservation
    @OneToMany(mappedBy = "parcel", fetch = FetchType.EAGER)
    List<Reservation> reservations;

    //<-customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}

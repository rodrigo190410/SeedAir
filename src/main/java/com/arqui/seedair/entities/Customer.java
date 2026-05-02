package com.arqui.seedair.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Integer phone;
    //<->user 1:1
    @OneToOne
    @JoinColumn(name = "user_id", unique = true,nullable = false)
    private User user;
    //->review

    //->parcel

    //->reservation

}

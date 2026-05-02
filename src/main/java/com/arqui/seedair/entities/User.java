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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    //->Authority
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_authorities",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "authority_id", referencedColumnName = "id", nullable = false)
            }
    )
    private List<Authority> authorities;
    //<->customer 1:1
    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Customer customer;
}

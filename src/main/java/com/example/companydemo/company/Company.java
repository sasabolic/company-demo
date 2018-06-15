package com.example.companydemo.company;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String city;

    private String country;

    private String email;

    private String phoneNumber;

    public Company(String name, String address, String city, String country, String email, String phoneNumber) {
        Objects.requireNonNull(name, "Companies' name cannot be null!");
        Objects.requireNonNull(address, "Companies' address cannot be null!");
        Objects.requireNonNull(city, "Companies' city cannot be null!");
        Objects.requireNonNull(country, "Companies' country cannot be null!");

        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void update(String name, String address, String city, String country, String email, String phoneNumber) {
        Objects.requireNonNull(name, "Companies' name cannot be null!");
        Objects.requireNonNull(address, "Companies' address cannot be null!");
        Objects.requireNonNull(city, "Companies' city cannot be null!");
        Objects.requireNonNull(country, "Companies' country cannot be null!");

        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}

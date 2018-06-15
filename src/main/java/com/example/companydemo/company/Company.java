package com.example.companydemo.company;

import com.example.companydemo.owner.Owner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Company entity.
 *
 * @author Sasa Bolic
 */
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

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "company_owner",
            joinColumns = { @JoinColumn(name = "company_id") },
            inverseJoinColumns = { @JoinColumn(name = "owner_id") }
    )
    List<Owner> owners = new ArrayList<>();

    public Company(String name, String address, String city, String country, String email, String phoneNumber, List<Owner> owners) {
        Objects.requireNonNull(name, "Companies' name cannot be null!");
        Objects.requireNonNull(address, "Companies' address cannot be null!");
        Objects.requireNonNull(city, "Companies' city cannot be null!");
        Objects.requireNonNull(country, "Companies' country cannot be null!");
        Objects.requireNonNull(owners, "Companies' list of owners cannot be null!");

        if (owners.isEmpty()) {
            throw new IllegalArgumentException("Companies' list of owners cannot be empty!");
        }

        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.owners = owners;
    }

    public void update(String name, String address, String city, String country, String email, String phoneNumber, List<Owner> owners) {
        Objects.requireNonNull(name, "Companies' name cannot be null!");
        Objects.requireNonNull(address, "Companies' address cannot be null!");
        Objects.requireNonNull(city, "Companies' city cannot be null!");
        Objects.requireNonNull(country, "Companies' country cannot be null!");
        Objects.requireNonNull(owners, "Companies' list of owners cannot be null!");

        if (owners.isEmpty()) {
            throw new IllegalArgumentException("Companies' list of owners cannot be empty!");
        }


        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.owners = owners;
    }
}

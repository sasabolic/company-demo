package com.example.companydemo.owner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Owner entity.
 *
 * @author Sasa Bolic
 */
@Entity
@Getter
@NoArgsConstructor
@ToString
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Owner(String name) {
        Objects.requireNonNull(name, "Owner's name cannot be null!");

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Owner's name cannot be empty!");
        }

        this.name = name;
    }
}

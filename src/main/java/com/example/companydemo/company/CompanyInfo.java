package com.example.companydemo.company;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Company information.
 *
 * @author Sasa Bolic
 */
@AllArgsConstructor
@Getter
public class CompanyInfo {

    private String name;
    private String address;
    private String city;
    private String country;
    private String email;
    private String phoneNumber;
    private List<Long> ownerIds;
}
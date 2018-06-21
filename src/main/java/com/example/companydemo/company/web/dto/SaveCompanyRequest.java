package com.example.companydemo.company.web.dto;

import com.example.companydemo.company.CompanyInfo;
import com.example.companydemo.core.Phone;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * Save company request DTO.
 *
 * @author Sasa Bolic
 */
@Getter
public class SaveCompanyRequest {

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Address cannot be empty")
    private String address;

    @NotEmpty(message = "City cannot be empty")
    private String city;

    @NotEmpty(message = "Country cannot be empty")
    private String country;

    @Email(message = "Value must be a well-formed email address")
    private String email;

    @Phone(message = "Phone number value should start with '+' and contain 7 to 15 digits")
    private String phoneNumber;

    @NotEmpty(message = "Owners list cannot be empty")
    private Set<Long> ownerIds;

    @JsonCreator
    public SaveCompanyRequest(@JsonProperty("name") String name,
                              @JsonProperty("address") String address,
                              @JsonProperty("city") String city,
                              @JsonProperty("country") String country,
                              @JsonProperty("email") String email,
                              @JsonProperty("phone_number") String phoneNumber,
                              @JsonProperty("owners") Set<Long> ownerIds) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.country = country;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.ownerIds = ownerIds;
    }

    public CompanyInfo toCompanyInfo() {
        return new CompanyInfo(this.name, this.address, this.city, this.country, this.email, this.phoneNumber, this.ownerIds);
    }
}

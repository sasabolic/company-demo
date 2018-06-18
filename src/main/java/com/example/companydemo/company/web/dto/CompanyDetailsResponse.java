package com.example.companydemo.company.web.dto;

import com.example.companydemo.owner.web.dto.OwnerResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Company details response DTO.
 *
 * @author Sasa Bolic
 */
@AllArgsConstructor
@Getter
public class CompanyDetailsResponse {

    private Long id;

    private String name;

    private String address;

    private String city;

    private String country;

    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private List<OwnerResponse> owners;
}

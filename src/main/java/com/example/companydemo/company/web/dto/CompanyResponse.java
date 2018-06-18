package com.example.companydemo.company.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Company response DTO.
 *
 * @author Sasa Bolic
 */
@AllArgsConstructor
@Getter
public class CompanyResponse {

    private Long id;

    private String name;

    private String address;

    private String city;

    private String country;
}

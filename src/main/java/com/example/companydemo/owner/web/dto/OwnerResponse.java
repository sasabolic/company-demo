package com.example.companydemo.owner.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Owner response DTO.
 *
 * @author Sasa Bolic
 */
@AllArgsConstructor
@Getter
public class OwnerResponse {

    private Long id;

    private String name;
}

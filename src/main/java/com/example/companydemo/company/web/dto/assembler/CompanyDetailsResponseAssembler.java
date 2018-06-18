package com.example.companydemo.company.web.dto.assembler;

import com.example.companydemo.company.Company;
import com.example.companydemo.company.web.dto.CompanyDetailsResponse;
import com.example.companydemo.core.GenericResponseAssembler;

/**
 * Assembler interface for converting {@link CompanyDetailsResponse} to DTOs.
 *
 * @author Sasa Bolic
 */
public interface CompanyDetailsResponseAssembler extends GenericResponseAssembler<Company, CompanyDetailsResponse> {
}

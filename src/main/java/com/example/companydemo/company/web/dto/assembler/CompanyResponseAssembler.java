package com.example.companydemo.company.web.dto.assembler;

import com.example.companydemo.company.Company;
import com.example.companydemo.company.web.dto.CompanyResponse;
import com.example.companydemo.core.GenericResponseAssembler;

/**
 * Assembler interface for converting {@link CompanyResponse} to DTOs.
 *
 * @author Sasa Bolic
 */
public interface CompanyResponseAssembler extends GenericResponseAssembler<Company, CompanyResponse> {
}

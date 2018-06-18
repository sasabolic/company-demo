package com.example.companydemo.company.web.dto.assembler;

import com.example.companydemo.company.Company;
import com.example.companydemo.company.web.dto.CompanyResponse;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link CompanyResponseAssembler}.
 *
 * @author Sasa Bolic
 */
@Component
public class DefaultCompanyResponseAssembler implements CompanyResponseAssembler {

    @Override
    public CompanyResponse of(Company entity) {
        return new CompanyResponse(entity.getId(), entity.getName(), entity.getAddress(), entity.getCity(), entity.getCountry());
    }

}

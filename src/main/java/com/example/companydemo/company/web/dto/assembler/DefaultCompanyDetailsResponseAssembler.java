package com.example.companydemo.company.web.dto.assembler;

import com.example.companydemo.company.Company;
import com.example.companydemo.company.web.dto.CompanyDetailsResponse;
import com.example.companydemo.owner.web.dto.assembler.OwnerResponseAssembler;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link CompanyDetailsResponseAssembler}.
 *
 * @author Sasa Bolic
 */
@Component
public class DefaultCompanyDetailsResponseAssembler implements CompanyDetailsResponseAssembler {

    private final OwnerResponseAssembler ownerResponseAssembler;

    public DefaultCompanyDetailsResponseAssembler(OwnerResponseAssembler ownerResponseAssembler) {
        this.ownerResponseAssembler = ownerResponseAssembler;
    }

    @Override
    public CompanyDetailsResponse of(Company entity) {
        return new CompanyDetailsResponse(entity.getId(), entity.getName(), entity.getAddress(), entity.getCity(), entity.getCountry(), entity.getEmail(), entity.getPhoneNumber(), ownerResponseAssembler.of(entity.getOwners()));
    }

}

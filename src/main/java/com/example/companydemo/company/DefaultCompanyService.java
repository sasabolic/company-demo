package com.example.companydemo.company;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link CompanyService}.
 *
 * @author Sasa Bolic
 */
@Service
@Transactional
public class DefaultCompanyService implements CompanyService {

    private final CompanyRepository companyRepository;

    public DefaultCompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> findAll() {
        return this.companyRepository.findAll();
    }

    @Override
    public Company findById(Long id) {
        return this.companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(String.format("Company with id '%d' does not exist", id)));
    }

    @Override
    public Company save(Company company) {
        return this.companyRepository.save(company);
    }

    @Override
    public Company update(Long id, Company company) {
        final Company currentCompany = this.companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(String.format("Company with id '%d' does not exist", id)));

        currentCompany.update(company.getName(), company.getAddress(), company.getCity(), company.getCountry(), company.getEmail(), company.getPhoneNumber(), company.getOwners());

        return this.companyRepository.save(company);
    }

}

package com.example.companydemo.company;

import com.example.companydemo.owner.Owner;
import com.example.companydemo.owner.OwnerNotFoundException;
import com.example.companydemo.owner.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    private final OwnerRepository ownerRepository;

    public DefaultCompanyService(CompanyRepository companyRepository, OwnerRepository ownerRepository) {
        this.companyRepository = companyRepository;
        this.ownerRepository = ownerRepository;
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
    public Company save(CompanyInfo company) {
        this.companyRepository.findByNameAndCountry(company.getName(), company.getCountry())
                .ifPresent(f -> {
                    throw new CompanyUniqueViolationException(String.format("Company with name '%s' located in '%s' already exits", company.getName(), company.getCountry()));
                });

        final List<Owner> owners = findOwners(company.getOwnerIds(), "Could not create new company");

        return this.companyRepository.save(new Company(company.getName(), company.getAddress(), company.getCity(), company.getCountry(), company.getEmail(), company.getPhoneNumber(), owners));
    }

    @Override
    public Company update(Long id, CompanyInfo company) {
        this.companyRepository.findByNameAndCountry(company.getName(), company.getCountry())
                .ifPresent(f -> {
                    throw new CompanyUniqueViolationException(String.format("Company with name '%s' located in '%s' already exits", company.getName(), company.getCountry()));
                });

        final Company currentCompany = this.companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(String.format("Company with id '%d' does not exist", id)));

        final List<Owner> owners = findOwners(company.getOwnerIds(), "Could not update new company");

        currentCompany.update(company.getName(), company.getAddress(), company.getCity(), company.getCountry(), company.getEmail(), company.getPhoneNumber(), owners);

        return this.companyRepository.save(currentCompany);
    }

    private List<Owner> findOwners(List<Long> ownerIds, String errorMessage) {
        List<Exception> exceptions = new ArrayList<>();
        List<Owner> owners = new ArrayList<>();
        ownerIds.forEach(ownerId -> {
            try {
                final Owner owner = ownerRepository.findById(ownerId)
                        .orElseThrow(() -> new OwnerNotFoundException(String.format("Owner with id '%d' does not exist", ownerId)));

                owners.add(owner);
            } catch (OwnerNotFoundException ex) {
                exceptions.add(ex);
            }

        });

        if (!exceptions.isEmpty()) {
            throw new CompanyException(errorMessage, exceptions);
        }

        return owners;
    }

}

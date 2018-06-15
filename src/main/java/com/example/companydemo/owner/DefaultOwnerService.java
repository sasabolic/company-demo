package com.example.companydemo.owner;

import com.example.companydemo.company.Company;
import com.example.companydemo.company.CompanyNotFoundException;
import com.example.companydemo.company.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link OwnerService}.
 *
 * @author Sasa Bolic
 */
@Service
@Transactional
public class DefaultOwnerService implements OwnerService {

    private final OwnerRepository ownerRepository;

    public DefaultOwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public List<Owner> findAll() {
        return this.ownerRepository.findAll();
    }

}

package com.example.companydemo.owner;

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

package com.example.companydemo.owner;

import com.example.companydemo.company.Company;

/**
 * Exception thrown if {@link Owner} is not found.
 *
 * @author Sasa Bolic
 */
public class OwnerNotFoundException extends RuntimeException {

    public OwnerNotFoundException(String message) {
        super(message);
    }
}

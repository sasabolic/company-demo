package com.example.companydemo.company;

/**
 * Exception thrown if {@link Company} is not found.
 *
 * @author Sasa Bolic
 */
public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException(String message) {
        super(message);
    }
}

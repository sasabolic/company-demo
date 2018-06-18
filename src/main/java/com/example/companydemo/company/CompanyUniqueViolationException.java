package com.example.companydemo.company;

/**
 * Exception thrown in case {@code Company} is not unique.
 *
 * @author Sasa Bolic
 */
public class CompanyUniqueViolationException extends RuntimeException {

    public CompanyUniqueViolationException(String message) {
        super(message);
    }
}

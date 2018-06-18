package com.example.companydemo.company;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception thrown in case creation or updating of {@link Company} is not successful.
 *
 * @author Sasa Bolic
 */
@Getter
public class CompanyException extends RuntimeException {

    private final List<Exception> exceptions = new ArrayList<>();

    public CompanyException(String message) {
        super(message);
    }

    public CompanyException(String message, List<Exception> exceptions) {
        this(message);
        this.exceptions.addAll(exceptions);
    }

    public boolean isEmpty() {
        return this.exceptions.isEmpty();
    }
}

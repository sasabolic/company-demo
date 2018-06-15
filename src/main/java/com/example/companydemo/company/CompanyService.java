package com.example.companydemo.company;

import java.util.List;

/**
 * Interface for actions on {@code Company}.
 *
 * @author Sasa Bolic
 */
public interface CompanyService {

    /**
     * Returns list of {@code Company}.
     *
     * @return the list
     */
    List<Company> findAll();

    /**
     * Returns {@code Company} with given {@code id}.
     *
     * @param id the id
     * @return the company
     */
    Company findById(Long id);

    /**
     * Creates new {@code Film} with given values.
     *
     * @param company the company
     * @return the company
     */
    Company save(Company company);

    /**
     * Updates {@code Customer} with given values.
     *
     * @param id      the id
     * @param company the company
     * @return the company
     */
    Company update(Long id, Company company);

}
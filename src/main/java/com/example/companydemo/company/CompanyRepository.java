package com.example.companydemo.company;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface to manage {@code Company} instances.
 *
 * @author Sasa Bolic
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {

}
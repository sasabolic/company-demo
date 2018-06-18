package com.example.companydemo.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Repository interface to manage {@code Company} instances.
 *
 * @author Sasa Bolic
 */
@Transactional(readOnly = true)
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c LEFT JOIN FETCH c.owners WHERE c.id = ?1")
    @Override
    Optional<Company> findById(Long id);
}
package com.example.companydemo.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository interface to manage {@code Owner} instances.
 *
 * @author Sasa Bolic
 */
@Transactional(readOnly = true)
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query("SELECT o FROM Owner o WHERE UPPER(o.name) LIKE UPPER(concat('%', ?1,'%'))")
    List<Owner> findByName(String name);
}
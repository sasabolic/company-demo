package com.example.companydemo.owner;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface to manage {@code Owner} instances.
 *
 * @author Sasa Bolic
 */
public interface OwnerRepository extends JpaRepository<Owner, Long> {

}
package com.example.companydemo.owner;

import java.util.List;

/**
 * Interface for actions on {@code Owner}.
 *
 * @author Sasa Bolic
 */
public interface OwnerService {

    /**
     * Returns list of {@code Owner}.
     *
     * @return the list
     */
    List<Owner> findAll();

}
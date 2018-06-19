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
     * @param name the name of owner
     * @return the list
     */
    List<Owner> findAll(String name);

}
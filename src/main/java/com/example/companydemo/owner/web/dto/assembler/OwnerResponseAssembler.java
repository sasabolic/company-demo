package com.example.companydemo.owner.web.dto.assembler;

import com.example.companydemo.core.GenericResponseAssembler;
import com.example.companydemo.owner.Owner;
import com.example.companydemo.owner.web.dto.OwnerResponse;

/**
 * Assembler interface for converting {@link OwnerResponse} to DTOs.
 *
 * @author Sasa Bolic
 */
public interface OwnerResponseAssembler extends GenericResponseAssembler<Owner, OwnerResponse> {
}

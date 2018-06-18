package com.example.companydemo.owner.web.dto.assembler;

import com.example.companydemo.owner.Owner;
import com.example.companydemo.owner.web.dto.OwnerResponse;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link OwnerResponseAssembler}.
 *
 * @author Sasa Bolic
 */
@Component
public class DefaultOwnerResponseAssembler implements OwnerResponseAssembler {

    @Override
    public OwnerResponse of(Owner entity) {
        return new OwnerResponse(entity.getId(), entity.getName());
    }

}

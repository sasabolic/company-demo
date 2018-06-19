package com.example.companydemo.owner.web;


import com.example.companydemo.owner.Owner;
import com.example.companydemo.owner.OwnerService;
import com.example.companydemo.owner.web.dto.OwnerResponse;
import com.example.companydemo.owner.web.dto.assembler.OwnerResponseAssembler;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST owner resources.
 *
 * @author Sasa Bolic
 */
@RestController
@RequestMapping("/owners")
@Api(tags = "owner")
public class OwnerController {

    private final OwnerService ownerService;
    private final OwnerResponseAssembler ownerResponseAssembler;

    public OwnerController(OwnerService ownerService, OwnerResponseAssembler ownerResponseAssembler) {
        this.ownerService = ownerService;
        this.ownerResponseAssembler = ownerResponseAssembler;
    }

    @GetMapping
    public ResponseEntity<List<OwnerResponse>> getAll(@RequestParam(required = false) String name) {
        final List<Owner> owners = this.ownerService.findAll(name);

        return ResponseEntity.ok(this.ownerResponseAssembler.of(owners));
    }
}

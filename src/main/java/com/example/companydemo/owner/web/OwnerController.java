package com.example.companydemo.owner.web;


import com.example.companydemo.owner.Owner;
import com.example.companydemo.owner.OwnerService;
import com.example.companydemo.owner.web.dto.OwnerResponse;
import com.example.companydemo.owner.web.dto.assembler.OwnerResponseAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST owner resources.
 *
 * @author Sasa Bolic
 */
@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;
    private final OwnerResponseAssembler ownerResponseAssembler;

    public OwnerController(OwnerService ownerService, OwnerResponseAssembler ownerResponseAssembler) {
        this.ownerService = ownerService;
        this.ownerResponseAssembler = ownerResponseAssembler;
    }

    @GetMapping
    public ResponseEntity<List<OwnerResponse>> getAll() {
        final List<Owner> owners = this.ownerService.findAll();

        return ResponseEntity.ok(this.ownerResponseAssembler.of(owners));
    }
}
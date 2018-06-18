package com.example.companydemo.company.web;


import com.example.companydemo.company.Company;
import com.example.companydemo.company.CompanyService;
import com.example.companydemo.company.web.dto.CompanyDetailsResponse;
import com.example.companydemo.company.web.dto.CompanyResponse;
import com.example.companydemo.company.web.dto.SaveCompanyRequest;
import com.example.companydemo.company.web.dto.assembler.CompanyDetailsResponseAssembler;
import com.example.companydemo.company.web.dto.assembler.CompanyResponseAssembler;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * REST company resources.
 *
 * @author Sasa Bolic
 */
@RestController
@RequestMapping("/companies")
@Api(tags = "company")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyResponseAssembler companyResponseAssembler;
    private final CompanyDetailsResponseAssembler companyDetailsResponseAssembler;

    public CompanyController(CompanyService companyService, CompanyResponseAssembler companyResponseAssembler, CompanyDetailsResponseAssembler companyDetailsResponseAssembler) {
        this.companyService = companyService;
        this.companyResponseAssembler = companyResponseAssembler;
        this.companyDetailsResponseAssembler = companyDetailsResponseAssembler;
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAll() {
        final List<Company> companies = this.companyService.findAll();

        return ResponseEntity.ok(this.companyResponseAssembler.of(companies));
    }

    @GetMapping(value = "/{companyId}")
    public ResponseEntity<CompanyDetailsResponse> get(@PathVariable Long companyId) {
        final Company company = this.companyService.findById(companyId);

        return ResponseEntity.ok(this.companyDetailsResponseAssembler.of(company));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid SaveCompanyRequest saveCompanyRequest) {
        final Company company = this.companyService.save(saveCompanyRequest.toCompanyInfo());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{companyId}")
                .buildAndExpand(company.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/{companyId}")
    public ResponseEntity<Void> update(@PathVariable Long companyId, @RequestBody @Valid SaveCompanyRequest saveCompanyRequest) {
        this.companyService.update(companyId, saveCompanyRequest.toCompanyInfo());

        return ResponseEntity.noContent().build();
    }
}

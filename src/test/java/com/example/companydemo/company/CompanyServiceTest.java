package com.example.companydemo.company;

import com.example.companydemo.owner.Owner;
import com.example.companydemo.owner.OwnerDataFixtures;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
public class CompanyServiceTest {

    private CompanyService service;

    @Mock
    private static CompanyRepository companyRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        service = new DefaultCompanyService(companyRepository);
    }

    @Test
    public void whenFindAllThenReturnListOfCustomers() {
        doReturn(CompanyDataFixtures.companies()).when(companyRepository).findAll();

        final List<Company> result = service.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(3);
    }

    @Test
    public void whenFindByIdThenReturnCompany() {
        doReturn(Optional.of(CompanyDataFixtures.company())).when(companyRepository).findById(isA(Long.class));

        final Company result = service.findById(1L);

        assertThat(result).isNotNull();
    }

    @Test
    public void whenFindByNonExistingIdThenThrowException() {
        final long customerId = 1L;

        thrown.expect(CompanyNotFoundException.class);
        thrown.expectMessage("Company with id '" + customerId + "' does not exist");

        doReturn(Optional.ofNullable(null)).when(companyRepository).findById(isA(Long.class));

        service.findById(customerId);
    }

    @Test
    public void whenCreatingCompanyThenReturnCompany() {
        final String name = "Tesla";
        final Set<Owner> owners = Collections.singleton(OwnerDataFixtures.owner("Elon Musk"));

        doReturn(CompanyDataFixtures.companyWithNameAndOwners(name, owners)).when(companyRepository).save(isA(Company.class));

        final Company result = service.save(CompanyDataFixtures.companyWithNameAndOwners(name, owners));

        assertThat(result).isNotNull();
        assertThat(result).hasFieldOrPropertyWithValue("name", name);
        assertThat(result).hasFieldOrPropertyWithValue("owners", owners);
    }

    @Test
    public void whenUpdatingCompanyThenReturnCompany() {
        final String name = "Tesla";
        final Set<Owner> owners = Collections.singleton(OwnerDataFixtures.owner("Elon Musk"));
        final String newName = "New Tesla";
        final Set<Owner> newOwners = Collections.singleton(OwnerDataFixtures.owner("Jian Yang"));

        doReturn(Optional.of(CompanyDataFixtures.companyWithNameAndOwners(name, owners))).when(companyRepository).findById(isA(Long.class));
        doReturn(CompanyDataFixtures.companyWithNameAndOwners(newName, newOwners)).when(companyRepository).save(isA(Company.class));

        final Company result = service.update(5L, CompanyDataFixtures.companyWithNameAndOwners(newName, newOwners));

        assertThat(result).isNotNull();
        assertThat(result).hasFieldOrPropertyWithValue("name", newName);
        assertThat(result).hasFieldOrPropertyWithValue("owners", newOwners);
    }

    @Test
    public void whenUpdatingNonExistingCompanyThenThrowException() {
        final Company company = CompanyDataFixtures.company();
        final long companyId = 1L;

        thrown.expect(CompanyNotFoundException.class);
        thrown.expectMessage("Company with id '" + companyId + "' does not exist");

        doReturn(Optional.ofNullable(null)).when(companyRepository).findById(isA(Long.class));

        service.update(companyId, company);
    }
}

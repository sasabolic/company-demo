package com.example.companydemo.company;

import com.example.companydemo.owner.Owner;
import com.example.companydemo.owner.OwnerDataFixtures;
import com.example.companydemo.owner.OwnerRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@RunWith(SpringRunner.class)
public class CompanyServiceTest {

    private CompanyService service;

    @Mock
    private static CompanyRepository companyRepository;

    @Mock
    private static OwnerRepository ownerRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        service = new DefaultCompanyService(companyRepository, ownerRepository);
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
        final Owner owner = OwnerDataFixtures.owner("Elon Musk");
        final Set<Owner> owners = Collections.singleton(owner);

        doReturn(Optional.of(owner)).when(ownerRepository).findById(eq(1L));
        doReturn(CompanyDataFixtures.companyWithNameAndOwners(name, owners)).when(companyRepository).save(isA(Company.class));

        final Company result = service.save(CompanyDataFixtures.companyInfoWithNameAndOwnerIds(name, Stream.of(1L).collect(Collectors.toSet())));

        assertThat(result).isNotNull();
        assertThat(result).hasFieldOrPropertyWithValue("name", name);
        assertThat(result).hasFieldOrPropertyWithValue("owners", owners);
    }

    @Test
    public void whenCreatingCompanyWithNonExistingOwnersThenThrowException() {
        final String name = "Tesla";

        doReturn(Optional.ofNullable(null)).when(ownerRepository).findById(anyLong());

        Company result = null;
        try {
            result = service.save(CompanyDataFixtures.companyInfoWithNameAndOwnerIds(name, Stream.of(1L).collect(Collectors.toSet())));
        } catch (CompanyException ex) {
            assertThat(ex).isNotNull();
            assertThat(ex.isEmpty()).isFalse();
            assertThat(ex.getExceptions()).hasSize(1);
            assertThat(ex.getExceptions()).extracting(Exception::getMessage)
                    .containsExactly(
                            "Owner with id '1' does not exist"
                    );
        }

        assertThat(result).isNull();
    }

    @Test
    public void whenCreatingCompanyWithDuplicateNameAndCountryThenThrowException() {
        final Company company = spy(CompanyDataFixtures.company());

        thrown.expect(CompanyUniqueViolationException.class);
        thrown.expectMessage("Company with name '" + company.getName() + "' located in '" + company.getCountry() + "' already exits");

        doReturn(Optional.of(company)).when(companyRepository).findByNameAndCountry(isA(String.class), isA(String.class));

        service.save(CompanyDataFixtures.fromCompany(company));
    }

    @Test
    public void whenUpdatingCompanyThenReturnCompany() {
        final String name = "Tesla";
        final Set<Owner> owners = Collections.singleton(OwnerDataFixtures.owner("Elon Musk"));
        final String newName = "New Tesla";
        final Owner newOwner = OwnerDataFixtures.owner("Jian Yang");
        final Set<Owner> newOwners = Collections.singleton(newOwner);

        doReturn(Optional.of(newOwner)).when(ownerRepository).findById(eq(2L));
        doReturn(Optional.of(CompanyDataFixtures.companyWithNameAndOwners(name, owners))).when(companyRepository).findById(isA(Long.class));
        doReturn(CompanyDataFixtures.companyWithNameAndOwners(newName, newOwners)).when(companyRepository).save(isA(Company.class));

        final Company result = service.update(5L, CompanyDataFixtures.companyInfoWithNameAndOwnerIds(newName, Stream.of(2L).collect(Collectors.toSet())));

        assertThat(result).isNotNull();
        assertThat(result).hasFieldOrPropertyWithValue("name", newName);
        assertThat(result).hasFieldOrPropertyWithValue("owners", newOwners);
    }

    @Test
    public void whenUpdatingNonExistingCompanyThenThrowException() {
        final long companyId = 1L;

        thrown.expect(CompanyNotFoundException.class);
        thrown.expectMessage("Company with id '" + companyId + "' does not exist");

        doReturn(Optional.ofNullable(null)).when(companyRepository).findById(isA(Long.class));

        service.update(companyId, CompanyDataFixtures.companyInfoWithNameAndOwnerIds("Tesla",Stream.of(1L).collect(Collectors.toSet())));
    }

    @Test
    public void whenUpdatingCompanyWithNonExistingOwnersThenThrowException() {
        final String name = "Tesla";
        final long companyId = 1L;
        final Company company = spy(CompanyDataFixtures.company());

        doReturn(companyId).when(company).getId();
        doReturn(Optional.of(company)).when(companyRepository).findById(anyLong());

        doReturn(Optional.ofNullable(null)).when(ownerRepository).findById(anyLong());

        Company result = null;
        try {
            result = service.update(companyId, CompanyDataFixtures.companyInfoWithNameAndOwnerIds(name, Stream.of(1L).collect(Collectors.toSet())));
        } catch (CompanyException ex) {
            assertThat(ex).isNotNull();
            assertThat(ex.isEmpty()).isFalse();
            assertThat(ex.getExceptions()).hasSize(1);
            assertThat(ex.getExceptions()).extracting(Exception::getMessage)
                    .containsExactly(
                            "Owner with id '1' does not exist"
                    );
        }

        assertThat(result).isNull();

        assertThat(result).isNull();
    }

    @Test
    public void whenUpdatingCompanyWithSameIdAndWithDuplicateNameAndCountryThenReturnCompany() {
        final Owner newOwner = spy(OwnerDataFixtures.owner("New Owner"));
        final long ownerId = 2L;

        final Company existingCompany = spy(CompanyDataFixtures.companyWithOwners(Collections.singleton(OwnerDataFixtures.owner("Old Owner"))));
        final Company company = spy(CompanyDataFixtures.companyWithOwners(Collections.singleton(newOwner)));
        final long companyId = 5L;

        doReturn(companyId).when(existingCompany).getId();
        doReturn(ownerId).when(newOwner).getId();
        doReturn(Optional.of(newOwner)).when(ownerRepository).findById(eq(ownerId));
        doReturn(Optional.of(existingCompany)).when(companyRepository).findByNameAndCountry(isA(String.class), isA(String.class));
        doReturn(Optional.of(existingCompany)).when(companyRepository).findById(isA(Long.class));

        service.update(companyId, CompanyDataFixtures.fromCompany(company));
    }

    @Test
    public void whenUpdatingCompanyWithDuplicateNameAndCountryThenThrowException() {
        final Company company = spy(CompanyDataFixtures.company());
        final long companyId = 5L;

        thrown.expect(CompanyUniqueViolationException.class);
        thrown.expectMessage("Company with name '" + company.getName() + "' located in '" + company.getCountry() + "' already exits");

        doReturn(6L).when(company).getId();
        doReturn(Optional.of(company)).when(companyRepository).findByNameAndCountry(isA(String.class), isA(String.class));

        service.update(companyId, CompanyDataFixtures.fromCompany(company));
    }
}

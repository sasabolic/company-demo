package com.example.companydemo.company;

import com.example.companydemo.owner.Owner;
import com.example.companydemo.owner.OwnerDataFixtures;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CompanyRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompanyRepository repository;

    @Test
    public void whenSaveThenSizeIncreased() {
        Long before = repository.count();

        repository.save(CompanyDataFixtures.company());

        Long after = repository.count();

        assertThat(after).isEqualTo(before.intValue() + 1);
    }

    @Test
    public void whenSaveThenSearchAllContainsSavedResult() {
        Long before = repository.count();

        Company company = repository.save(CompanyDataFixtures.company());

        List<Company> result = repository.findAll();

        assertThat(result).hasSize(before.intValue() + 1);
        assertThat(result).contains(company);
    }

    @Test
    public void whenSaveThenReturnCorrectResult() {
        final Owner owner = entityManager.find(Owner.class, 1L);

        final Company company = CompanyDataFixtures.companyWithOwners(Stream.of(owner, owner, owner).collect(Collectors.toSet()));

        final Company result = repository.save(company);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(company.getName());
        assertThat(result.getAddress()).isEqualTo(company.getAddress());
        assertThat(result.getCity()).isEqualTo(company.getCity());
        assertThat(result.getCountry()).isEqualTo(company.getCountry());
        assertThat(result.getEmail()).isEqualTo(company.getEmail());
        assertThat(result.getPhoneNumber()).isEqualTo(company.getPhoneNumber());
        assertThat(result.getOwners()).isEqualTo(company.getOwners());
    }

    @Test
    public void whenSaveWithMultipleOwnersThenReturnCorrectResult() {
        final Set<Owner> owners = new HashSet<>(OwnerDataFixtures.owners());
        final Company company = CompanyDataFixtures.companyWithOwners(owners);

        final Company result = repository.save(company);

        assertThat(result).isNotNull();
        assertThat(result.getOwners().size()).isEqualTo(owners.size());
        assertThat(result.getOwners()).isEqualTo(company.getOwners());
    }

    @Test
    public void whenFindAllThenReturnCorrectResult() {
        final Owner owner = entityManager.find(Owner.class, 1L);
        Long before = repository.count();

        entityManager.persist(CompanyDataFixtures.companyWithNameAndOwners("Tesla", Collections.singleton(owner)));
        entityManager.persist(CompanyDataFixtures.companyWithNameAndOwners("BMW", Collections.singleton(owner)));
        entityManager.persist(CompanyDataFixtures.companyWithNameAndOwners("Audi", Collections.singleton(owner)));
        entityManager.flush();

        final List<Company> result = repository.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(before.intValue() + 3);
    }

    @Test
    public void whenFindByIdThenReturnCorrectResult() {
        final Owner owner = entityManager.find(Owner.class, 1L);
        final Company company = CompanyDataFixtures.companyWithNameAndOwners("Tesla", Collections.singleton(owner));
        entityManager.persist(company);
        entityManager.flush();

        final Optional<Company> result = repository.findById(company.getId());

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getName()).isEqualTo(company.getName());
        assertThat(result.get().getAddress()).isEqualTo(company.getAddress());
        assertThat(result.get().getCity()).isEqualTo(company.getCity());
        assertThat(result.get().getCountry()).isEqualTo(company.getCountry());
        assertThat(result.get().getEmail()).isEqualTo(company.getEmail());
        assertThat(result.get().getPhoneNumber()).isEqualTo(company.getPhoneNumber());
        assertThat(result.get().getOwners()).isEqualTo(company.getOwners());
    }

    @Test
    public void whenSearchByNameAndCountryThenReturnCorrectResult() {
        final Owner owner = entityManager.find(Owner.class, 1L);
        final Company company = CompanyDataFixtures.companyWithNameAndOwners("Tesla", Collections.singleton(owner));
        entityManager.persist(company);
        entityManager.flush();

        final Optional<Company> result = repository.findByNameAndCountry("tesla", "usa");

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getName()).isEqualTo(company.getName());
        assertThat(result.get().getAddress()).isEqualTo(company.getAddress());
        assertThat(result.get().getCity()).isEqualTo(company.getCity());
        assertThat(result.get().getCountry()).isEqualTo(company.getCountry());
        assertThat(result.get().getEmail()).isEqualTo(company.getEmail());
        assertThat(result.get().getPhoneNumber()).isEqualTo(company.getPhoneNumber());
        assertThat(result.get().getOwners()).isEqualTo(company.getOwners());
    }

}

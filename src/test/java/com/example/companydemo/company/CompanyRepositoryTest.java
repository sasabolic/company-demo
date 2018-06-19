package com.example.companydemo.company;

import com.example.companydemo.owner.Owner;
import com.example.companydemo.owner.OwnerDataFixtures;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
        final Set<Owner> owners = OwnerDataFixtures.owners().stream().collect(Collectors.toSet());
        final Company company = CompanyDataFixtures.companyWithOwners(owners);

        final Company result = repository.save(company);

        assertThat(result).isNotNull();
        assertThat(result.getOwners().size()).isEqualTo(owners.size());
        assertThat(result.getOwners()).isEqualTo(company.getOwners());
    }

    @Test
    public void whenFindAllThenReturnCorrectResult() {
        Long before = repository.count();

        entityManager.persist(CompanyDataFixtures.companyWithName("Tesla"));
        entityManager.persist(CompanyDataFixtures.companyWithName("BMW"));
        entityManager.persist(CompanyDataFixtures.companyWithName("Audi"));
        entityManager.flush();

        final List<Company> result = repository.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(before.intValue() + 3);
    }

    @Test
    public void whenFindByIdThenReturnCorrectResult() {
        final Company company = CompanyDataFixtures.companyWithName("Tesla");
        entityManager.persist(company);
        entityManager.flush();

        final Company result = repository.findById(company.getId()).get();

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
    public void whenSearchByNameAndCountryThenReturnCorrectResult() {
        final Company company = CompanyDataFixtures.companyWithName("Tesla");
        entityManager.persist(company);
        entityManager.flush();

        final Company result = repository.findByNameAndCountry("tesla", "usa").get();

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(company.getName());
        assertThat(result.getAddress()).isEqualTo(company.getAddress());
        assertThat(result.getCity()).isEqualTo(company.getCity());
        assertThat(result.getCountry()).isEqualTo(company.getCountry());
        assertThat(result.getEmail()).isEqualTo(company.getEmail());
        assertThat(result.getPhoneNumber()).isEqualTo(company.getPhoneNumber());
        assertThat(result.getOwners()).isEqualTo(company.getOwners());
    }

}

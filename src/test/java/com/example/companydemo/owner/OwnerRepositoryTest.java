package com.example.companydemo.owner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository repository;

    @Test
    public void whenSearchAllThenReturnResult() {
        final List<Owner> result = repository.findAll();

        assertThat(result).isNotEmpty();
    }

    @Test
    public void whenSearchByNameThenReturnListContainingThatName() {
        final List<Owner> result = repository.findByName("el");

        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
        assertThat(result).extracting(Owner::getName).containsExactly("Elon Musk", "Gavin Belson");
    }

    @Test
    public void whenSearchByNonExistingNameThenReturnEmptyList() {
        final List<Owner> result = repository.findByName("non-existing");

        assertThat(result).isEmpty();
    }
}

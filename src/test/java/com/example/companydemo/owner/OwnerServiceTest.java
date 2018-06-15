package com.example.companydemo.owner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
public class OwnerServiceTest {

    private OwnerService service;

    @Mock
    private static OwnerRepository ownerRepository;

    @Before
    public void setUp() {
        service = new DefaultOwnerService(ownerRepository);
    }

    @Test
    public void whenFindAllThenReturnListOfCustomers() {
        doReturn(OwnerDataFixtures.owners()).when(ownerRepository).findAll();

        final List<Owner> result = service.findAll();

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(3);
    }

}

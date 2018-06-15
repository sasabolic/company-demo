package com.example.companydemo.owner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class OwnerTest {

    private Owner owner;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp()  {
        owner = OwnerDataFixtures.owner();
    }

    @Test
    public void whenNewInstanceThenInitialized() {

        assertThat(owner).isNotNull();
    }

    @Test
    public void givenNameNullWhenNewInstanceThenThrowException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Owner's name cannot be null!");

        owner = OwnerDataFixtures.owner(null);
    }

    @Test
    public void givenNameEmptyWhenNewInstanceThenThrowException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Owner's name cannot be empty!");

        owner = OwnerDataFixtures.owner("");
    }
}

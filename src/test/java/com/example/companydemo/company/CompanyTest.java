package com.example.companydemo.company;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class CompanyTest {

    private Company company;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp()  {
        company = CompanyDataFixtures.company();
    }

    @Test
    public void whenNewInstanceThenInitialized() {

        assertThat(company).isNotNull();
    }

    @Test
    public void givenNameNullWhenNewInstanceThenThrowException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Companies' name cannot be null!");

        company = CompanyDataFixtures.company(null, "3500 Deer Creek Road, CA 94304", "Palo Alto", "USA", "office@tesla.com", "+16506815000");
    }

    @Test
    public void givenAddressNullWhenNewInstanceThenThrowException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Companies' address cannot be null!");

        company = CompanyDataFixtures.company("Tesla", null, "Palo Alto", "USA", "office@tesla.com", "+16506815000");
    }

    @Test
    public void givenCityNullWhenNewInstanceThenThrowException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Companies' city cannot be null!");

        company = CompanyDataFixtures.company("Tesla", "3500 Deer Creek Road, CA 94304", null, "USA", "office@tesla.com", "+16506815000");
    }

    @Test
    public void givenCountryNullWhenNewInstanceThenThrowException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Companies' country cannot be null!");

        company = CompanyDataFixtures.company("Tesla", "3500 Deer Creek Road, CA 94304", "Palo Alto", null, "office@tesla.com", "+16506815000");
    }

    @Test
    public void givenEmailNullWhenNewInstanceThenInitialized() {
        company = CompanyDataFixtures.company("Tesla", "3500 Deer Creek Road, CA 94304", "Palo Alto", "USA", null, "+16506815000");
    }

    @Test
    public void givenPhoneNumberNullWhenNewInstanceThenInitialized() {
        company = CompanyDataFixtures.company("Tesla", "3500 Deer Creek Road, CA 94304", "Palo Alto", "USA", "office@tesla.com", null);
    }
}

package com.example.companydemo.company;

import com.example.companydemo.owner.Owner;
import com.example.companydemo.owner.OwnerDataFixtures;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;
import java.util.List;

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

        company = CompanyDataFixtures.company(null, "3500 Deer Creek Road, CA 94304", "Palo Alto", "USA", "office@tesla.com", "+16506815000", Collections.singletonList(OwnerDataFixtures.owner()));
    }

    @Test
    public void givenAddressNullWhenNewInstanceThenThrowException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Companies' address cannot be null!");

        company = CompanyDataFixtures.company("Tesla", null, "Palo Alto", "USA", "office@tesla.com", "+16506815000", Collections.singletonList(OwnerDataFixtures.owner()));
    }

    @Test
    public void givenCityNullWhenNewInstanceThenThrowException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Companies' city cannot be null!");

        company = CompanyDataFixtures.company("Tesla", "3500 Deer Creek Road, CA 94304", null, "USA", "office@tesla.com", "+16506815000", Collections.singletonList(OwnerDataFixtures.owner()));
    }

    @Test
    public void givenCountryNullWhenNewInstanceThenThrowException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Companies' country cannot be null!");

        company = CompanyDataFixtures.company("Tesla", "3500 Deer Creek Road, CA 94304", "Palo Alto", null, "office@tesla.com", "+16506815000", Collections.singletonList(OwnerDataFixtures.owner()));
    }

    @Test
    public void givenEmailNullWhenNewInstanceThenInitialized() {
        company = CompanyDataFixtures.company("Tesla", "3500 Deer Creek Road, CA 94304", "Palo Alto", "USA", null, "+16506815000", Collections.singletonList(OwnerDataFixtures.owner()));
    }

    @Test
    public void givenPhoneNumberNullWhenNewInstanceThenInitialized() {
        company = CompanyDataFixtures.company("Tesla", "3500 Deer Creek Road, CA 94304", "Palo Alto", "USA", "office@tesla.com", null, Collections.singletonList(OwnerDataFixtures.owner()));
    }

    @Test
    public void givenOwnerListNullWhenNewInstanceThenThrowException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Companies' list of owners cannot be null!");

        company = CompanyDataFixtures.company("Tesla", "3500 Deer Creek Road, CA 94304", "Palo Alto", "USA", "office@tesla.com", "+16506815000", null);
    }

    @Test
    public void givenOwnerListEmptyWhenNewInstanceThenThrowException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Companies' list of owners cannot be empty!");

        company = CompanyDataFixtures.company("Tesla", "3500 Deer Creek Road, CA 94304", "Palo Alto", "USA", "office@tesla.com", "+16506815000", Collections.emptyList());
    }

    @Test
    public void whenUpdateThenFieldsChanged() {
        final String newName = "New Name";
        final String newAddress = "Newish";
        final String newCity = "Belgrade";
        final String newCountry = "Serbia";
        final String newEmail = "new@email.com";
        final String newPhoneNumber = "+381888888888";
        final List<Owner> newOwners = Collections.singletonList(OwnerDataFixtures.owner("New Owner"));

        company.update(newName, newAddress, newCity, newCountry, newEmail, newPhoneNumber, newOwners);

        assertThat(company).hasFieldOrPropertyWithValue("name", newName);
        assertThat(company).hasFieldOrPropertyWithValue("address", newAddress);
        assertThat(company).hasFieldOrPropertyWithValue("city", newCity);
        assertThat(company).hasFieldOrPropertyWithValue("country", newCountry);
        assertThat(company).hasFieldOrPropertyWithValue("email", newEmail);
        assertThat(company).hasFieldOrPropertyWithValue("phoneNumber", newPhoneNumber);
        assertThat(company).hasFieldOrPropertyWithValue("owners", newOwners);
    }

    @Test
    public void givenNameNullWhenUpdateThenThrowException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Companies' name cannot be null!");

        final String newName = null;
        final String newAddress = "Newish";
        final String newCity = "Belgrade";
        final String newCountry = "Serbia";
        final String newEmail = "new@email.com";
        final String newPhoneNumber = "+381888888888";
        final List<Owner> newOwners = Collections.singletonList(OwnerDataFixtures.owner("New Owner"));

        company.update(newName, newAddress, newCity, newCountry, newEmail, newPhoneNumber, newOwners);
    }

    @Test
    public void givenAddressNullWhenUpdateThenThrowException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Companies' address cannot be null!");

        final String newName = "New Name";
        final String newAddress = null;
        final String newCity = "Belgrade";
        final String newCountry = "Serbia";
        final String newEmail = "new@email.com";
        final String newPhoneNumber = "+381888888888";
        final List<Owner> newOwners = Collections.singletonList(OwnerDataFixtures.owner("New Owner"));

        company.update(newName, newAddress, newCity, newCountry, newEmail, newPhoneNumber, newOwners);
    }

    @Test
    public void givenCityNullWhenUpdateThenThrowException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Companies' city cannot be null!");

        final String newName = "New Name";
        final String newAddress = "Newish";
        final String newCity = null;
        final String newCountry = "Serbia";
        final String newEmail = "new@email.com";
        final String newPhoneNumber = "+381888888888";
        final List<Owner> newOwners = Collections.singletonList(OwnerDataFixtures.owner("New Owner"));

        company.update(newName, newAddress, newCity, newCountry, newEmail, newPhoneNumber, newOwners);
    }

    @Test
    public void givenCountryNullWhenUpdateThenThrowException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Companies' country cannot be null!");

        final String newName = "New Name";
        final String newAddress = "Newish";
        final String newCity = "Belgrade";
        final String newCountry = null;
        final String newEmail = "new@email.com";
        final String newPhoneNumber = "+381888888888";
        final List<Owner> newOwners = Collections.singletonList(OwnerDataFixtures.owner("New Owner"));

        company.update(newName, newAddress, newCity, newCountry, newEmail, newPhoneNumber, newOwners);
    }

    @Test
    public void givenEmailNullWhenUpdateThenInitialized() {
        final String newName = "New Name";
        final String newAddress = "Newish";
        final String newCity = "Belgrade";
        final String newCountry = "Serbia";
        final String newEmail = null;
        final String newPhoneNumber = "+381888888888";
        final List<Owner> newOwners = Collections.singletonList(OwnerDataFixtures.owner("New Owner"));

        company.update(newName, newAddress, newCity, newCountry, newEmail, newPhoneNumber, newOwners);
    }

    @Test
    public void givenPhoneNumberNullWhenUpdateThenInitialized() {
        final String newName = "New Name";
        final String newAddress = "Newish";
        final String newCity = "Belgrade";
        final String newCountry = "Serbia";
        final String newEmail = "new@email.com";
        final String newPhoneNumber = null;
        final List<Owner> newOwners = Collections.singletonList(OwnerDataFixtures.owner("New Owner"));

        company.update(newName, newAddress, newCity, newCountry, newEmail, newPhoneNumber, newOwners);
    }

    @Test
    public void givenOwnerListNullWhenUpdateThenThrowException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Companies' list of owners cannot be null!");

        final String newName = "New Name";
        final String newAddress = "Newish";
        final String newCity = "Belgrade";
        final String newCountry = "Serbia";
        final String newEmail = "new@email.com";
        final String newPhoneNumber = "+381888888888";
        final List<Owner> newOwners = null;

        company.update(newName, newAddress, newCity, newCountry, newEmail, newPhoneNumber, newOwners);
    }

    @Test
    public void givenOwnerListEmptyWhenUpdateThenThrowException() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Companies' list of owners cannot be empty!");

        final String newName = "New Name";
        final String newAddress = "Newish";
        final String newCity = "Belgrade";
        final String newCountry = "Serbia";
        final String newEmail = "new@email.com";
        final String newPhoneNumber = "+381888888888";
        final List<Owner> newOwners = Collections.emptyList();

        company.update(newName, newAddress, newCity, newCountry, newEmail, newPhoneNumber, newOwners);
    }
}

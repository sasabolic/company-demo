package com.example.companydemo.company.web.dto;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SaveCompanyRequestValidationTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void whenRequestValidThenViolationListEmpty() {
        SaveCompanyRequest request = new SaveCompanyRequest("Name", "Address", "City", "Country", "mail@email.com", "+381883838", Arrays.asList(1L, 2L));
        final Set<ConstraintViolation<SaveCompanyRequest>> validate = validator.validate(request);

        assertThat(validate).isEmpty();
    }

    @Test
    public void whenRequestInvalidNameThenViolationForName() {
        SaveCompanyRequest request = new SaveCompanyRequest("", "Address", "City", "Country", "mail@email.com", "+381883838", Arrays.asList(1L, 2L));
        final Set<ConstraintViolation<SaveCompanyRequest>> validate = validator.validate(request);

        assertThat(validate).isNotEmpty();
        assertThat(validate).hasSize(1);
        assertThat(validate).extracting(ConstraintViolation::getMessage).containsOnly("Name cannot be empty");
    }

    @Test
    public void whenRequestInvalidAddressThenViolationForAddress() {
        SaveCompanyRequest request = new SaveCompanyRequest("Name", "", "City", "Country", "mail@email.com", "+381883838", Arrays.asList(1L, 2L));
        final Set<ConstraintViolation<SaveCompanyRequest>> validate = validator.validate(request);

        assertThat(validate).isNotEmpty();
        assertThat(validate).hasSize(1);
        assertThat(validate).extracting(ConstraintViolation::getMessage).containsOnly("Address cannot be empty");
    }

    @Test
    public void whenRequestInvalidCityThenViolationForCity() {
        SaveCompanyRequest request = new SaveCompanyRequest("Name", "Address", "", "Country", "mail@email.com", "+381883838", Arrays.asList(1L, 2L));
        final Set<ConstraintViolation<SaveCompanyRequest>> validate = validator.validate(request);

        assertThat(validate).isNotEmpty();
        assertThat(validate).hasSize(1);
        assertThat(validate).extracting(ConstraintViolation::getMessage).containsOnly("City cannot be empty");
    }

    @Test
    public void whenRequestInvalidCountryThenViolationForCountry() {
        SaveCompanyRequest request = new SaveCompanyRequest("Name", "Address", "City", "", "mail@email.com", "+381883838", Arrays.asList(1L, 2L));
        final Set<ConstraintViolation<SaveCompanyRequest>> validate = validator.validate(request);

        assertThat(validate).isNotEmpty();
        assertThat(validate).hasSize(1);
        assertThat(validate).extracting(ConstraintViolation::getMessage).containsOnly("Country cannot be empty");
    }

    @Test
    public void whenRequestInvalidEmailThenViolationForEmail() {
        SaveCompanyRequest request = new SaveCompanyRequest("Name", "Address", "City", "Country", "test", "+381883838", Arrays.asList(1L, 2L));
        final Set<ConstraintViolation<SaveCompanyRequest>> validate = validator.validate(request);

        assertThat(validate).isNotEmpty();
        assertThat(validate).hasSize(1);
        assertThat(validate).extracting(ConstraintViolation::getMessage).containsOnly("Value must be a well-formed email address");
    }

    @Test
    public void whenRequestInvalidPhoneNumberThenViolationForPhoneNumber() {
        SaveCompanyRequest request = new SaveCompanyRequest("Name", "Address", "City", "Country", "mail@email.com", "test", Arrays.asList(1L, 2L));
        final Set<ConstraintViolation<SaveCompanyRequest>> validate = validator.validate(request);

        assertThat(validate).isNotEmpty();
        assertThat(validate).hasSize(1);
        assertThat(validate).extracting(ConstraintViolation::getMessage).containsOnly("Value must be a well-formed phone number");
    }

    @Test
    public void whenRequestInvalidOwnersListThenViolationForOwnersList() {
        SaveCompanyRequest request = new SaveCompanyRequest("Name", "Address", "City", "Country", "mail@email.com", "+381883838", null);
        final Set<ConstraintViolation<SaveCompanyRequest>> validate = validator.validate(request);

        assertThat(validate).isNotEmpty();
        assertThat(validate).hasSize(1);
        assertThat(validate).extracting(ConstraintViolation::getMessage).containsOnly("Owners list cannot be empty");
    }

    @Test
    public void whenRequestValidWithEmptyEmailThenViolationListEmpty() {
        SaveCompanyRequest request = new SaveCompanyRequest("Name", "Address", "City", "Country", "", "+381883838", Arrays.asList(1L, 2L));
        final Set<ConstraintViolation<SaveCompanyRequest>> validate = validator.validate(request);

        assertThat(validate).isEmpty();
    }

    @Test
    public void whenRequestValidWithEmptyPhoneNumberThenViolationListEmpty() {
        SaveCompanyRequest request = new SaveCompanyRequest("Name", "Address", "City", "Country", "mail@email.com", "+381883838", Arrays.asList(1L, 2L));
        final Set<ConstraintViolation<SaveCompanyRequest>> validate = validator.validate(request);

        assertThat(validate).isEmpty();
    }

    @Test
    public void whenRequestInvalidAllFieldsThenViolationListNotEmpty() {
        SaveCompanyRequest request = new SaveCompanyRequest("", "", "", "", "email.com", "phone", null);
        final Set<ConstraintViolation<SaveCompanyRequest>> validate = validator.validate(request);

        assertThat(validate).isNotEmpty();
        assertThat(validate).hasSize(7);
    }
}

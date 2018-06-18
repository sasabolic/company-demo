package com.example.companydemo.company.web;

import com.example.companydemo.company.*;
import com.example.companydemo.company.web.dto.assembler.CompanyDetailsResponseAssembler;
import com.example.companydemo.company.web.dto.assembler.CompanyResponseAssembler;
import com.example.companydemo.company.web.dto.assembler.DefaultCompanyDetailsResponseAssembler;
import com.example.companydemo.company.web.dto.assembler.DefaultCompanyResponseAssembler;
import com.example.companydemo.owner.Owner;
import com.example.companydemo.owner.OwnerDataFixtures;
import com.example.companydemo.owner.web.dto.assembler.DefaultOwnerResponseAssembler;
import com.example.companydemo.owner.web.dto.assembler.OwnerResponseAssembler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CompanyController.class)
public class CompanyControllerTest {

    @TestConfiguration
    static class TestConfig {

        @Bean
        public OwnerResponseAssembler ownerResponseAssembler() {
            return new DefaultOwnerResponseAssembler();
        }

        @Bean
        public CompanyResponseAssembler companyResponseAssembler() {
            return new DefaultCompanyResponseAssembler();
        }

        @Bean
        public CompanyDetailsResponseAssembler companyDetailsResponseAssembler() {
            return new DefaultCompanyDetailsResponseAssembler(ownerResponseAssembler());
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompanyService companyService;

    @Test
    public void whenGetAllThenReturnStatusOK() throws Exception {
        given(this.companyService.findAll())
                .willReturn(CompanyDataFixtures.companies());

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/companies")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetAllThenReturnJsonList() throws Exception {
        given(this.companyService.findAll())
                .willReturn(CompanyDataFixtures.companies());

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/companies")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", equalTo("Tesla")))
                .andExpect(jsonPath("$[0].address", equalTo("3500 Deer Creek Road, CA 94304")))
                .andExpect(jsonPath("$[1].city", equalTo("Palo Alto")))
                .andExpect(jsonPath("$[1].country", equalTo("USA")))
                .andExpect(jsonPath("$[2].email").doesNotExist())
                .andExpect(jsonPath("$[2].phone_number").doesNotExist())
                .andExpect(jsonPath("$[2].owners").doesNotExist());
    }

    @Test
    public void whenGetByIdThenReturnStatusOK() throws Exception {
        final long companyId = 1L;
        final Company company = spy(CompanyDataFixtures.company());

        given(company.getId()).willReturn(companyId);
        given(this.companyService.findById(anyLong())).willReturn(company);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/companies/{companyId}", 1L)
                .accept(MediaType.APPLICATION_JSON);


        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetByIdThenReturnJson() throws Exception {
        final String name = "New Test Inc.";
        final String address = "Knez Mihajlova 22";
        final String city = "Belgrade";
        final String country = "Serbia";
        final String email = "office@newtest.com";
        final String phoneNumber = "+381999999999";
        final String ownerName = "Petar Petrovic";
        final List<Owner> owners = Collections.singletonList(OwnerDataFixtures.owner(ownerName));
        final long companyId = 1L;
        final Company company = spy(CompanyDataFixtures.company(name, address, city, country, email, phoneNumber, owners));

        given(company.getId()).willReturn(companyId);
        given(this.companyService.findById(anyLong())).willReturn(company);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/companies/{companyId}", companyId)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name", equalTo(name)))
                .andExpect(jsonPath("$.address", equalTo(address)))
                .andExpect(jsonPath("$.city", equalTo(city)))
                .andExpect(jsonPath("$.country", equalTo(country)))
                .andExpect(jsonPath("$.email", equalTo(email)))
                .andExpect(jsonPath("$.phone_number", equalTo(phoneNumber)))
                .andExpect(jsonPath("$.owners").isArray())
                .andExpect(jsonPath("$.owners", hasSize(1)))
                .andExpect(jsonPath("$.owners[0].name", equalTo(ownerName)));
    }

    @Test
    public void whenGetNonExistingThenReturnStatusNotFound() throws Exception {
        final Long companyId = 1L;
        final String message = "Company with id '" + companyId + "' does not exist";

        given(this.companyService.findById(anyLong())).willThrow(new CompanyNotFoundException(message));

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/companies/{companyId}", companyId)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenGetNonExistingThenReturnJsonError() throws Exception {
        final Long companyId = 1L;
        final String message = "Company with id '" + companyId + "' does not exist";

        given(this.companyService.findById(anyLong())).willThrow(new CompanyNotFoundException(message));

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/companies/{companyId}", companyId)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo(message)));
    }

    @Test
    public void whenCreateThenReturnStatusCreated() throws Exception {
        final long companyId = 1L;
        final Company company = spy(CompanyDataFixtures.company());

        given(company.getId()).willReturn(companyId);
        given(this.companyService.save(any(CompanyInfo.class))).willReturn(company);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/companies")
                .content(CompanyDataFixtures.json())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void whenCreateThenReturnLocationHeader() throws Exception {
        final long companyId = 1L;
        final Company company = spy(CompanyDataFixtures.company());

        given(company.getId()).willReturn(companyId);
        given(this.companyService.save(any(CompanyInfo.class))).willReturn(company);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/companies")
                .content(CompanyDataFixtures.json())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/companies/" + companyId));
    }

    @Test
    public void whenCreateEmptyRequestThenReturnStatusBadRequest() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/companies")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenCreateEmptyRequestThenReturnJsonError() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/companies")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status", equalTo(400)))
                .andExpect(jsonPath("$.message", equalTo("Validation failed")))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(5)));
    }

    @Test
    public void whenCreateWithDuplicateNameAndCountryThenReturnStatusConflict() throws Exception {
        final String name = "Tesla";
        final String country = "USA";
        final String message = "Company with name '" + name + "' located in '" + country + "' already exits";

        given(this.companyService.save(any(CompanyInfo.class))).willThrow(new CompanyUniqueViolationException(message));

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/companies")
                .content(CompanyDataFixtures.json())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void whenCreateWithDuplicateNameAndCountryThenReturnJsonError() throws Exception {
        final String name = "Tesla";
        final String country = "USA";
        final String message = "Company with name '" + name + "' located in '" + country + "' already exits";

        given(this.companyService.save(any(CompanyInfo.class))).willThrow(new CompanyUniqueViolationException(message));

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/companies")
                .content(CompanyDataFixtures.json())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status", equalTo(409)))
                .andExpect(jsonPath("$.message", equalTo(message)));
    }

    @Test
    public void whenUpdateThenReturnStatusNoContent() throws Exception {
        final long companyId = 1L;
        final Company company = spy(CompanyDataFixtures.company());

        given(company.getId()).willReturn(companyId);
        given(this.companyService.update(anyLong(), any(CompanyInfo.class))).willReturn(company);

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/companies/{companies}", 1L)
                .content(CompanyDataFixtures.json())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void whenUpdateNonExistingThenReturnStatusNotFound() throws Exception {
        final Long companyId = 1L;
        final String message = "Company with id '" + companyId + "' does not exist";

        given(this.companyService.update(anyLong(), any(CompanyInfo.class))).willThrow(new CompanyNotFoundException(message));

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/companies/{companyId}", companyId)
                .content(CompanyDataFixtures.json())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenUpdateNonExistingThenReturnJsonError() throws Exception {
        final Long companyId = 1L;
        final String message = "Company with id '" + companyId + "' does not exist";

        given(this.companyService.update(anyLong(), any(CompanyInfo.class))).willThrow(new CompanyNotFoundException(message));

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/companies/{companyId}", companyId)
                .content(CompanyDataFixtures.json())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo(message)));
    }

    @Test
    public void whenUpdateEmptyRequestThenReturnStatusBadRequest() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/companies/{companyId}", 1L)
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenUpdateEmptyRequestThenReturnJsonError() throws Exception {
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/companies/{companyId}", 1L)
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status", equalTo(400)))
                .andExpect(jsonPath("$.message", equalTo("Validation failed")))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(5)));
    }

    @Test
    public void whenUpdateWithDuplicateNameAndCountryThenReturnStatusConflict() throws Exception {
        final String name = "Tesla";
        final String country = "USA";
        final String message = "Company with name '" + name + "' located in '" + country + "' already exits";

        given(this.companyService.update(anyLong(), any(CompanyInfo.class))).willThrow(new CompanyUniqueViolationException(message));

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/companies/{companyId}", 1L)
                .content(CompanyDataFixtures.json())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void whenUpdateWithDuplicateNameAndCountryThenReturnJsonError() throws Exception {
        final String name = "Tesla";
        final String country = "USA";
        final String message = "Company with name '" + name + "' located in '" + country + "' already exits";

        given(this.companyService.update(anyLong(), any(CompanyInfo.class))).willThrow(new CompanyUniqueViolationException(message));

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/companies/{companyId}", 1L)
                .content(CompanyDataFixtures.json())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status", equalTo(409)))
                .andExpect(jsonPath("$.message", equalTo(message)));
    }
}
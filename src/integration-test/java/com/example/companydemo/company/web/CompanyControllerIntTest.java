package com.example.companydemo.company.web;

import com.example.companydemo.AbstractWebIntTest;
import com.example.companydemo.company.CompanyDataFixtures;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CompanyControllerIntTest extends AbstractWebIntTest {

    @Test
    public void whenGetAllThenReturnResult() throws Exception {
        mvc.perform(get("/companies").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", equalTo("Telenor ASA")))
                .andExpect(jsonPath("$[0].address", equalTo("Snarøyveien 30, N-1360 Fornebu, Norway")))
                .andExpect(jsonPath("$[0].city", equalTo("Oslo")))
                .andExpect(jsonPath("$[0].country", equalTo("Norway")))
                .andExpect(jsonPath("$[0].email").doesNotExist())
                .andExpect(jsonPath("$[0].phone_number").doesNotExist())
                .andExpect(jsonPath("$[0].owners").doesNotExist())
                .andExpect(jsonPath("$[1].name", equalTo("Apple")))
                .andExpect(jsonPath("$[1].address", equalTo("One Apple Park Way, CA 95014")))
                .andExpect(jsonPath("$[1].city", equalTo("Cupertino")))
                .andExpect(jsonPath("$[1].country", equalTo("USA")))
                .andExpect(jsonPath("$[1].email").doesNotExist())
                .andExpect(jsonPath("$[1].phone_number").doesNotExist())
                .andExpect(jsonPath("$[1].owners").doesNotExist());
    }

    @Test
    public void whenGetByIdThenReturnResult() throws Exception {
        mvc.perform(get("/companies/{companyId}", 2L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.name", equalTo("Apple")))
                .andExpect(jsonPath("$.address", equalTo("One Apple Park Way, CA 95014")))
                .andExpect(jsonPath("$.city", equalTo("Cupertino")))
                .andExpect(jsonPath("$.country", equalTo("USA")))
                .andExpect(jsonPath("$.email", equalTo("office@apple.com")))
                .andExpect(jsonPath("$.phone_number", equalTo("(408) 996–1010")))
                .andExpect(jsonPath("$.owners").isArray())
                .andExpect(jsonPath("$.owners", hasSize(2)))
                .andExpect(jsonPath("$.owners[0].name", equalTo("Richard Hendricks")))
                .andExpect(jsonPath("$.owners[1].name", equalTo("Gavin Belson")));
    }

    @Test
    public void whenGetNonExistingThenReturnError() throws Exception {
        final long companyId = 100L;
        mvc.perform(get("/companies/{companyId}", companyId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status", equalTo(404)))
                .andExpect(jsonPath("$.message", equalTo("Company with id '" + companyId + "' does not exist")));

    }

    @Test
    public void whenCreateWithDuplicateNameAndCountryThenReturnStatusConflict() throws Exception {
        final String name = "Apple";
        final String country = "USA";
        final String message = "Company with name '" + name + "' located in '" + country + "' already exits";


        mvc.perform(post("/companies")
                .content(CompanyDataFixtures.json(name, country))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.status", equalTo(409)))
                .andExpect(jsonPath("$.message", equalTo(message)));
    }

    @Test
    public void whenCreateThenReturnResult() throws Exception {
        mvc.perform(post("/companies")
                .content(CompanyDataFixtures.json())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/companies/3"));
    }

    @Test
    public void whenCreateEmptyRequestThenReturnJsonError() throws Exception {
        mvc.perform(post("/companies")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
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
}
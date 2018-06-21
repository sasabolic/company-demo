package com.example.companydemo.owner.web;

import com.example.companydemo.owner.Owner;
import com.example.companydemo.owner.OwnerDataFixtures;
import com.example.companydemo.owner.OwnerService;
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

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = OwnerController.class)
public class OwnerControllerTest {

    @TestConfiguration
    static class TestConfig {

        @Bean
        public OwnerResponseAssembler ownerResponseAssembler() {
            return new DefaultOwnerResponseAssembler();
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OwnerService ownerService;

    @Test
    public void whenGetAllThenReturnStatusOK() throws Exception {
        given(this.ownerService.findAll(null))
                .willReturn(OwnerDataFixtures.owners());

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/owners")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetAllThenReturnJsonList() throws Exception {
        given(this.ownerService.findAll(null))
                .willReturn(OwnerDataFixtures.owners());

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/owners")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", equalTo("Elon Musk")))
                .andExpect(jsonPath("$[1].name", equalTo("Richard Hendricks")))
                .andExpect(jsonPath("$[2].name", equalTo("Erlich Bachman")));
    }

    @Test
    public void whenGetAllByNameThenReturnJsonListContainingThatName() throws Exception {
        final String name = "elo";
        final long ownerId = 1L;
        final Owner owner = spy(OwnerDataFixtures.owner("Elon Musk"));

        given(owner.getId()).willReturn(ownerId);
        given(this.ownerService.findAll(name)).willReturn(Collections.singletonList(owner));

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/owners?name={name}", name)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", equalTo("Elon Musk")));
    }

    @Test
    public void whenGetAllByNonExistingTitleThenReturnEmptyJsonList() throws Exception {
        final String name = "non-existing";

        given(this.ownerService.findAll(name)).willReturn(Collections.emptyList());

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/owners?name={name}", name)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
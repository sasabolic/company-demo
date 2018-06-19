package com.example.companydemo.owner.web;

import com.example.companydemo.AbstractWebIntTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OwnerControllerIntTest extends AbstractWebIntTest {

    @Test
    public void whenGetAllThenReturnResult() throws Exception {
        mvc.perform(get("/owners"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", equalTo("Elon Musk")))
                .andExpect(jsonPath("$[1].name", equalTo("Richard Hendricks")))
                .andExpect(jsonPath("$[2].name", equalTo("Gavin Belson")));
    }

    @Test
    public void whenGetAllByNameThenReturnJsonListContainingThatName() throws Exception {
        final String name = "elo";

        mvc.perform(get("/owners?name={name}", name)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", equalTo("Elon Musk")));
    }
}

package com.example.companydemo;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractWebIntTest {

    @Autowired
    WebApplicationContext context;

    protected MockMvc mvc;

    @Before
    public void init() {

        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .defaultRequest(MockMvcRequestBuilders.get("/").locale(Locale.US))
                .build();
    }
}
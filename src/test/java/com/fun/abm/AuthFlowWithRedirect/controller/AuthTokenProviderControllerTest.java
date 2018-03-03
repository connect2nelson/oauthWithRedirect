package com.fun.abm.AuthFlowWithRedirect.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
public class AuthTokenProviderControllerTest {

    private MockMvc mvc;

    @Before
    public void setuip(){
        AuthTokenProviderController unitUnderTest = new AuthTokenProviderController();
        mvc = MockMvcBuilders.standaloneSetup(unitUnderTest).build();
    }

    @Test
    public void shouldReturnValidTokenWithRedirectOnValidInput() throws Exception {

        URI uri = new URI("/api/auth" + "?"
                + "state=valid_state" + "&"
                + "code=valid_code");

        mvc.perform(get(uri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/redirect"))
                .andExpect(cookie().exists("authtoken"));

    }
}
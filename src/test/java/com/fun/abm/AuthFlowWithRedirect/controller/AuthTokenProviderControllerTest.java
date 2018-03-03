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

    private static String GET_TOKEN_ENDPOINT = "/api/auth/token";


    @Before
    public void setuip() {
        AuthTokenProviderController unitUnderTest = new AuthTokenProviderController();
        mvc = MockMvcBuilders.standaloneSetup(unitUnderTest).build();
    }

    @Test
    public void shouldReturnValidTokenWithRedirectOnValidInput() throws Exception {


        URI uri = new URI(GET_TOKEN_ENDPOINT + "?"
                + "state=valid_state" + "&"
                + "code=valid_code");

        mvc.perform(get(uri))
                .andExpect(status().is3xxRedirection())
                .andExpect(cookie().exists("authtoken"))
                .andExpect(cookie().value("authtoken", "valid-authtoken"));


    }

    @Test
    public void shouldReturnResponseWithBadRequestHttpStatusCodeOnInValidInput() throws Exception {
        URI uri = new URI(GET_TOKEN_ENDPOINT + "?"
                + "state=invalid_state" + "&"
                + "code=invalid_code");

        mvc.perform(get(uri))
                .andExpect(status().isBadRequest())
                .andExpect(cookie().doesNotExist("authtoken"));

    }

    @Test
    public void shouldNotReturnValidTokenWhenStateAndCodeAreNotPassed() throws Exception {

        URI uri = new URI(GET_TOKEN_ENDPOINT);
        mvc.perform(get(uri))
                .andExpect(status().is4xxClientError())
                .andExpect(cookie().doesNotExist("authtoken"));

    }

    @Test
    public void shouldReturnValidTokenAsCookieWithDomainSet() throws Exception {

        URI uri = new URI(GET_TOKEN_ENDPOINT + "?"
                + "state=valid_state" + "&"
                + "code=valid_code");

        mvc.perform(get(uri))
                .andExpect(status().is3xxRedirection())
                .andExpect(cookie().domain("authtoken", "app.com"));

    }

    @Test
    public void shouldReturnValidTokenAsCookieWithPathSet() throws Exception {

        URI uri = new URI(GET_TOKEN_ENDPOINT + "?"
                + "state=valid_state" + "&"
                + "code=valid_code");

        mvc.perform(get(uri))
                .andExpect(status().is3xxRedirection())
                .andExpect(cookie().path("authtoken", "/api/auth/getToken"));

    }

    @Test
    public void shouldReturnValidTokenAsCookieWithARedirectToARedirectUrl() throws Exception {

        URI uri = new URI(GET_TOKEN_ENDPOINT + "?"
                + "state=valid_state" + "&"
                + "code=valid_code");

        mvc.perform(get(uri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/redirect"));

    }

}
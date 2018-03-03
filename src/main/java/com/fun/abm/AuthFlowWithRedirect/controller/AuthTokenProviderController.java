package com.fun.abm.AuthFlowWithRedirect.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

@Controller
class AuthTokenProviderController {

    private static final String PARAM_STATE = "state";
    private static final String PARAM_CODE = "code";

    private static final String GET_TOKEN_ENDPOINT = "/api/auth/token";
    private static final String REDIRECT_URI = "https://app.com/redirect";



    @GetMapping(value = GET_TOKEN_ENDPOINT)
    private ResponseEntity<String> getAuthToken(@RequestParam(PARAM_CODE) String code,
                                                @RequestParam(PARAM_STATE) String state,
                                                HttpServletResponse httpServletResponse) throws IOException {

        if (isInputValid(code, state)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Cookie authtokenCookie = createAuthTokenCookie();
        httpServletResponse.addCookie(authtokenCookie);
        httpServletResponse.sendRedirect(URLEncoder.encode(REDIRECT_URI, "UTF-8"));

        return null;
    }

    private Cookie createAuthTokenCookie() {
        Cookie authtokenCookie = new Cookie("authtoken", generateAuthToken());

        try {
            URI redirectURI = new URI(REDIRECT_URI);
            String domain = redirectURI.getHost();
            authtokenCookie.setDomain(domain);
            authtokenCookie.setPath("/api/auth/getToken");
            return authtokenCookie;
        } catch (URISyntaxException e) {

        }
        return null;

    }

    private String generateAuthToken() {
        return "valid-authtoken";
    }

    private boolean isInputValid(String code, String state) {
        return code.equals("invalid_code") || state.equals("invalid_state");
    }

}

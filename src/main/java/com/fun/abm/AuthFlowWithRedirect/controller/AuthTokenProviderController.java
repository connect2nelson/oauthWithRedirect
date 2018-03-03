package com.fun.abm.AuthFlowWithRedirect.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
class AuthTokenProviderController {

    private static final String PARAM_STATE = "state";
    private static final String PARAM_CODE = "code";

    private static final String  GET_TOKEN_ENDPOINT = "/api/auth/token";


    @GetMapping(value = GET_TOKEN_ENDPOINT)
    private ResponseEntity<String> getAuthToken(@RequestParam(PARAM_CODE) String code,
                                                @RequestParam(PARAM_STATE) String state,
                                                HttpServletResponse httpServletResponse) throws IOException {

        if (isInputValid(code, state)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Cookie authtokenCookie = createAuthTokenCookie();
        httpServletResponse.addCookie(authtokenCookie);
        httpServletResponse.sendRedirect("/redirect");

        return null;
    }

    private Cookie createAuthTokenCookie() {
        Cookie authtokenCookie = new Cookie("authtoken", generateAuthToken());
        authtokenCookie.setDomain("app.com");
        authtokenCookie.setPath("/api/auth/getToken");
        return authtokenCookie;
    }

    private String generateAuthToken() {
        return "valid-authtoken";
    }

    private boolean isInputValid(String code, String state) {
        return code.equals("invalid_code") || state.equals("invalid_state");
    }

}

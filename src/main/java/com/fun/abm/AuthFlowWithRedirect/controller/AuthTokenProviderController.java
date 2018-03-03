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


    @GetMapping(value = "/api/auth")
    private ResponseEntity<String> getAuthToken(@RequestParam(PARAM_CODE) String code,
                                                @RequestParam(PARAM_STATE) String state,
                                                HttpServletResponse httpServletResponse) {

        if (isInputValid(code, state)) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        Cookie authtoken = new Cookie("authtoken", "");
        httpServletResponse.addCookie(authtoken);

        try {
            httpServletResponse.sendRedirect("/redirect");
        } catch (IOException e) {

        }

        return  null;
    }

    private boolean isInputValid(String code, String state) {
        return code.equals("invalid_code")|| state.equals("invalid_state");
    }

}

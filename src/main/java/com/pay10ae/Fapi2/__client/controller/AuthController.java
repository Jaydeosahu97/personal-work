package com.pay10ae.Fapi2.__client.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.pay10ae.Fapi2.__client.dto.ParDTO;
import com.pay10ae.Fapi2.__client.dto.TokenParamsDTO;
import com.pay10ae.Fapi2.__client.service.AuthService;
import com.pay10ae.Fapi2.__client.utill.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/api")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private AuthService authService;
    private final RestTemplate restTemplate;

    public AuthController(AuthService authService, RestTemplate restTemplate) {
        this.authService = authService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/par")
    public String getPar(@RequestParam("url") String url) throws Exception {
        System.out.println("execute par endpoint");
        String parResponse = authService.preparePar(url);
        logger.info("par Response:{}", parResponse);

        return parResponse;
    }

    @GetMapping("/authorize")
    public String authorize(@RequestParam("url") String url, @RequestParam("client_id") String clientId, @RequestParam("request_uri") String requestUri) {
        return authService.authorize(url, clientId, requestUri);
    }

    @PostMapping("/acc-token")
    public String getToken(@RequestBody TokenParamsDTO tokenParam) {
        return authService.token(tokenParam);
    }

    @GetMapping("/code_verifier")
    public String codeVerifier(@RequestParam("code-challenge")String codeChallenge){
        return JwtUtil.code_verifier_map.get("code-challenge");
    }


    @PostMapping("/callback")
    public String handleCallback(@RequestBody String parResponse) {
        try {
            System.out.println("Received callback with PAR response: " + parResponse);

            return "Callback received successfully";
        } catch (Exception e) {
            System.err.println("Error processing callback: " + e.getMessage());
            return "Error processing callback";
        }
    }

}

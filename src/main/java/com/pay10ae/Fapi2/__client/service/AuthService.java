package com.pay10ae.Fapi2.__client.service;


import com.pay10ae.Fapi2.__client.dto.*;
import com.pay10ae.Fapi2.__client.utill.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Value("${spring.security.oauth2.client.registration.reg-client.client-id}")
    private String injectedClientId;
    @Value("${spring.security.oauth2.client.provider.spring.issuer-uri}")
    private String injectedIssuerUri;

    @Autowired
    private RestTemplate restTemplate;

    public String preparePar(String url) throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        JwtUtil jwtUtil = new JwtUtil();

        String privateKeyJwt = jwtUtil.preparePrivateKeyJwt(injectedClientId,injectedIssuerUri);
        String requestObject = jwtUtil.createRequestObject(injectedClientId,injectedIssuerUri);
        String clientId = injectedClientId;
        System.out.println("client ID "+ injectedClientId);
        System.out.println("private key jwt:"+privateKeyJwt);
        System.out.println("request object " +requestObject);




        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("request", requestObject); // Truncated for brevity
        body.add("client_assertion", privateKeyJwt); // Truncated for brevity
        body.add("client_assertion_type","urn:ietf:params:oauth:client-assertion-type:jwt-bearer");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            log.info("Response from api: " + response.getBody());
            return response.getBody();
        } catch (Exception e) {
            log.info("Request failed: " + e.getMessage());
        }

        return "some error occurred";

    }

    public RarDTO prepareRar() {
        String type = "https://scheme.example.com/payment_initiation";
        Behalf onBehalfOf = new Behalf("Ozone", "Ozone-CBUAE", "Other", "Identifier");
        String[] permissions = {"ReadTransactionsCredits", "ReadAccountsBasic", "ReadBalances", "ReadTransactionsBasic", "ReadTransactionsDetail", "ReadDirectDebits", "ReadBeneficiariesDetail", "ReadBeneficiariesBasic", "ReadScheduledPaymentsBasic", "ReadScheduledPaymentsDetail", "ReadStandingOrdersBasic", "ReadStandingOrdersDetail", "ReadParty", "ReadPartyUserIdentity", "ReadProduct"};
        OpenFinanceBilling openFinanceBilling = new OpenFinanceBilling("Retail", "AccountAggregation");
        Consent consent = new Consent(ZonedDateTime.now(ZoneId.of("UTC")).toString(), onBehalfOf, "121sdaf-23423-sdff21-2343", permissions, openFinanceBilling);
        Subscription subscription = new Subscription(new WebhookDTO("https://scheme.example.com/webhook", true));
        String[] locations = {"https://example.com/payments"};
        String[] actions = {"initiate", "status", "cancel"};
        InstructedAmount instructedAmount = new InstructedAmount(100.0, "EUR");
        String creditorName = "Merchant A";
        CreditorAccount creditorAccount = new CreditorAccount("DE02100100109307118603");
        String remittanceInformationUnstructured = "Payment for goods";
        return new RarDTO(type, consent, subscription, actions, locations, instructedAmount, creditorName, creditorAccount, remittanceInformationUnstructured);
    }

    public String token(TokenParamsDTO tokenParams) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", injectedClientId);
        params.add("redirect_uri", tokenParams.getRedirectUri());
        params.add("code", tokenParams.getCode());
        params.add("code_verifier", tokenParams.getCodeVerifier());
        params.add("grant_type", "authorization_code");
        params.add("client_assertion_type", "urn:ietf:params:oauth:client-assertion-type:jwt-bearer");
        params.add("client_assertion",tokenParams.getClientAssertion());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Combine headers and parameters
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        // Execute the request
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    tokenParams.getUrl(),
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            return response.toString();
        } catch (Exception e) {
            log.error("Error during request: {}", e.getMessage());
        }
        return "some error occurred";
    }

    public String authorize(String url,String clientId, String requestUri) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id",clientId);
        params.add("request_uri",requestUri);

        HttpHeaders headers = new HttpHeaders();

        // Combine headers and parameters
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        // Execute the request
        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // Output the response
            log.info("Response: {}", response.getBody());
            return response.toString();
        } catch (Exception e) {
            log.error("Error during request: {}", e.getMessage());
        }
        return "some error occurred";
    }
}

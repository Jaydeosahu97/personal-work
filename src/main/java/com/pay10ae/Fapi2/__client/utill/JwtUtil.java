package com.pay10ae.Fapi2.__client.utill;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.*;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pay10ae.Fapi2.__client.dto.*;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

public class JwtUtil {

    private static final long EXPIRY_SECONDS = 600;

//    @Value("${spring.security.oauth2.client.provider.spring.issuer-uri}")
//    private String injectedIssuerUri;
//
//    @Value("${spring.security.oauth2.client.registration.reg-client.client-id}")
//    private String injectedClientId;


    public static final HashMap<String, String> code_verifier_map = new HashMap();

    public static void storeCodeVerifier(String challenge, String verifier) {
        code_verifier_map.put(challenge, verifier);
    }

    public static String generateJwt(String clientId, String privateKey, String parEndpoint, String rarPayload) {
        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().audience(parEndpoint).issuer(clientId).claim("response_type", "code").claim("client_id", clientId).claim("scope", "openid payments").claim("authorization_details", rarPayload).expirationTime(new Date(System.currentTimeMillis() + EXPIRY_SECONDS * 1000)) // 10 mins expiry
                    .build();

            // Get the RSA private key for signing
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyUtils.getPrivateKey();

            // Create the PS256 signer
            JWSSigner signer = new RSASSASigner(rsaPrivateKey);

            // Create the SignedJWT with the specified claims and signer
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.PS256), claimsSet);
            signedJWT.sign(signer);

            // Return the JWT as a string
            return signedJWT.serialize();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateJwtWithPar(String clientId, ParDTO parDTO) throws JsonProcessingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String claims = objectMapper.writeValueAsString(parDTO);

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder().issuer(clientId).claim("response_type", "code").claim("client_id", clientId).claim("scope", "openid payments").claim("authorization_details", claims).expirationTime(new Date(System.currentTimeMillis() + EXPIRY_SECONDS * 1000)) // 10 mins expiry
                    .build();

            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyUtils.getPrivateKey();

            JWSSigner signer = new RSASSASigner(rsaPrivateKey);

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.PS256), claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to decode and extract claims from JWT
    public static Map<String, Object> getClaimsFromJwt(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            return claimsSet.getClaims();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String preparePrivateKeyJwt(String injectedClientId, String injectedIssuerUri) throws Exception {

        String privateKeyPem = new String(Files.readAllBytes(Paths.get("src/main/resources/private_key.pem")));
        PrivateKey privateKey = loadPrivateKey(privateKeyPem);
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.PS256).keyID(KeyUtils.getKidFromPrivateKey()).type(new JOSEObjectType("oauth-authz-req+jwt")).build();
        long timestamp = System.currentTimeMillis();
        int randomInt = new Random().nextInt(99999);
        String jwtId = timestamp + "-" + randomInt;
        JWTClaimsSet claims = new JWTClaimsSet.Builder().audience(injectedIssuerUri).expirationTime(new Date(System.currentTimeMillis() + 3600000)).issuer(injectedClientId).subject(injectedClientId).jwtID(jwtId).issueTime(new Date(System.currentTimeMillis()))

                .build();
        SignedJWT signedJWT = new SignedJWT(header, claims);
        signedJWT.sign(new RSASSASigner(privateKey));
        String privateKeyJWT = signedJWT.serialize();
        System.out.println("Private Key JWT: " + privateKeyJWT);
        return privateKeyJWT;
    }

    public static PrivateKey loadPrivateKey(String privateKeyPEM) throws Exception {
        StringReader stringReader = new StringReader(privateKeyPEM);
        PemReader pemReader = new PemReader(stringReader);
        PemObject pemObject = pemReader.readPemObject();
        byte[] keyBytes = pemObject.getContent();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public String createRequestObject(String injectedClientId, String injectedIssuerUri) throws Exception {
        String privateKeyPem = new String(Files.readAllBytes(Paths.get("src/main/resources/private_key.pem")));
        PrivateKey privateKey = loadPrivateKey(privateKeyPem);
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.PS256).keyID(KeyUtils.getKidFromPrivateKey()).type(new JOSEObjectType("oauth-authz-req+jwt")).build();
        long timestamp = System.currentTimeMillis();
        int randomInt = new Random().nextInt(99999);
        String jwtId = timestamp + "-" + randomInt;
        String codeVerifier = PKCEUtils.generateCodeVerifier();

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .audience(injectedIssuerUri)
                .expirationTime(new Date(System.currentTimeMillis() + 3600000))
                .issuer(injectedClientId)
                .claim("scope", "accounts openid")
                .claim("redirect_uri", "https://uatopenbanking.pay10.asia:7070/callback")
                .claim("client_id", injectedClientId)
                .claim("nonce", "1234567890abcdef").claim("state", "af0ifjsldkj")
                .notBeforeTime(new Date(System.currentTimeMillis()))
                .claim("response_type", "code")
                .claim("code_challenge_method", "S256")
                .claim("code_challenge", PKCEUtils.generateCodeVerifierAndChallenge())
                .claim("max_age", 3600)
                .claim("authorization_details", createAuthorizationDetails())
                .build();
        SignedJWT signedJWT = new SignedJWT(header, claims);
        signedJWT.sign(new RSASSASigner(privateKey));
        String privateKeyJWT = signedJWT.serialize();
        System.out.println("Request Object: " + privateKeyJWT);
        return privateKeyJWT;
    }

    private static List<Map<String, Object>> createAuthorizationDetails() {
        List<Map<String, Object>> authorizationDetails = List.of(Map.of("type", "urn:openfinanceuae:account-access-consent:v1.0", "locations", new String[]{"https://api.examplebank.com/accounts"}, "actions", new String[]{"read"}, "datatypes", new String[]{"balance", "transactions"}));
        return authorizationDetails;
    }


// Method to prepare request object JWT with PS256 signing
//public String prepareRequestObjectJwt() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
//    try {
//        String kid = KeyUtils.getKidFromPrivateKey();
//        ObjectMapper objectMapper = new ObjectMapper();
//        RSAPrivateKey privateKey = (RSAPrivateKey) KeyUtils.getPrivateKey();
//
//        UUID uuid = UUID.randomUUID();
//        String codeVerifier = PKCEUtils.generateCodeVerifier();
//        String codeChallenge = PKCEUtils.generateCodeChallenge(codeVerifier);
//
//        List<RarDTO> authorizationDetails = new ArrayList<>();
//        authorizationDetails.add(prepareRar());
//
//        ParDTO parDTO = new ParDTO();
//        parDTO.setAud(injectedIssuerUri);
//        parDTO.setExp(new Date(System.currentTimeMillis() + EXPIRY_SECONDS * 1000)); // 10 mins expiry
//        parDTO.setIat(new Date());
//        parDTO.setIss(injectedClientId);
//        parDTO.setScope("accounts openid");
//        parDTO.setRedirectUri("http://localhost:8082/login/oauth2/code/demo");
//        parDTO.setClientId(injectedClientId);
//        parDTO.setNonce("121sdaf-23423-sdff21-2343");
//        parDTO.setState("121sdaf-23423-sdff21-2343");
//        parDTO.setNbf(new Date());
//        parDTO.setResponseType("code");
//        parDTO.setCodeChallengeMethod("S256");
//        parDTO.setCodeChallenge(codeChallenge);
//        parDTO.setMaxAge(3600);
//        parDTO.setAuthorization_details(authorizationDetails.toArray(new RarDTO[0]));
//
//        String authorization_details = objectMapper.writeValueAsString(parDTO);
//
//        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
//                .audience(injectedIssuerUri)
//                .expirationTime(new Date(System.currentTimeMillis() + EXPIRY_SECONDS * 1000)) // 10 mins expiry
//                .issuer(injectedClientId)
//                .claim("response_type", "code")
//                .claim("client_id", injectedClientId)
//                .claim("authorization_details", authorization_details)
//                .claim("scope", "openid accounts")
//                .build();
//
//        // Create the PS256 signer
//        JWSSigner signer = new RSASSASigner(privateKey);
//
//        // Create the SignedJWT with the specified claims and signer
//        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.PS256), claimsSet);
//        signedJWT.sign(signer);
//
//        return signedJWT.serialize();
//    } catch (Exception e) {
//        e.printStackTrace();
//        return null;
//    }
//}
//
//
//public static RarDTO prepareRar() {
//    String type = "https://scheme.example.com/payment_initiation";
//    Behalf onBehalfOf = new Behalf("Ozone", "Ozone-CBUAE", "Other", "Identifier");
//    String[] permissions = {"ReadTransactionsCredits", "ReadAccountsBasic", "ReadBalances", "ReadTransactionsBasic", "ReadTransactionsDetail", "ReadDirectDebits", "ReadBeneficiariesDetail", "ReadBeneficiariesBasic", "ReadScheduledPaymentsBasic", "ReadScheduledPaymentsDetail", "ReadStandingOrdersBasic", "ReadStandingOrdersDetail", "ReadParty", "ReadPartyUserIdentity", "ReadProduct"};
//    OpenFinanceBilling openFinanceBilling = new OpenFinanceBilling("Retail", "AccountAggregation");
//    Consent consent = new Consent(ZonedDateTime.now(ZoneId.of("UTC")).toString(), onBehalfOf, "121sdaf-23423-sdff21-2343", permissions, openFinanceBilling);
//    Subscription subscription = new Subscription(new WebhookDTO("https://scheme.example.com/webhook", true));
//    String[] locations = {"https://example.com/payments"};
//    String[] actions = {"initiate", "status", "cancel"};
//    InstructedAmount instructedAmount = new InstructedAmount(100.0, "EUR");
//    String creditorName = "Merchant A";
//    CreditorAccount creditorAccount = new CreditorAccount("DE02100100109307118603");
//    String remittanceInformationUnstructured = "Payment for goods";
//    return new RarDTO(type, consent, subscription, actions, locations, instructedAmount, creditorName, creditorAccount, remittanceInformationUnstructured);
//}

//public void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
//    String privateKeyJwt = preparePrivateKeyJwt();
//    System.out.println("private key jwt: " + privateKeyJwt);
//
//    System.out.println("rar : " + prepareRar());
//
//    String requestObject = prepareRequestObjectJwt();
//    System.out.println("request object jwt: " + requestObject);
//
//}
}


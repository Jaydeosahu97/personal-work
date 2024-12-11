package com.pay10ae.Fapi2.__client.utill;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PKCEUtils {
//
//    public static String generateCodeVerifier() {
//        byte[] randomBytes = new byte[32];
//        new SecureRandom().nextBytes(randomBytes);
//        //return random generated code verifier
//        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
//    }
//
//    public static String generateCodeChallenge(String codeVerifier) throws NoSuchAlgorithmException {
//        MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        byte[] hash = digest.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
//        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
//    }

    private static final int CODE_VERIFIER_LENGTH = 64;
    public static final String CODE_CHALLENGE_METHOD = "S256";

    public static String generateCodeVerifier() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[CODE_VERIFIER_LENGTH];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public static String generateCodeChallenge(String codeVerifier) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(codeVerifier.getBytes());
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
    }

    public static String[] generatePKCE() throws NoSuchAlgorithmException {
        String codeVerifier = generateCodeVerifier();
        String codeChallenge = generateCodeChallenge(codeVerifier);
        return new String[]{codeVerifier, codeChallenge};
    }

    public static String generateCodeVerifierAndChallenge() {
        try {
            String[] pkce = generatePKCE();
            String codeVerifier = pkce[0];
            String codeChallenge = pkce[1];
            JwtUtil.storeCodeVerifier(codeChallenge, codeVerifier);
            System.out.println("code verifier:"+codeVerifier);
            System.out.println("code chanllenge:"+codeChallenge);
            return codeChallenge;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.pay10ae.Fapi2.__client.utill;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.Random;

public class PrivateKeyJwtUtil {

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
}

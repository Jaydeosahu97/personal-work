package com.pay10ae.Fapi2.__client.utill;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.UUID;

public class KeyUtils {
    public static PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        String filePath = "src/main/resources/private_key.pem";
        String privateKeyPem = new String(Files.readAllBytes(new File(filePath).toPath()));

        String privateKeyCleaned = privateKeyPem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyCleaned);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    public static String getKidFromPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        PrivateKey privateKey= getPrivateKey();
        PublicKey publicKey = getPublicKeyFromPrivateKey(privateKey);

        // Generate a "kid" based on the public key (you can hash the public key bytes to generate a unique ID)
        byte[] publicKeyBytes = publicKey.getEncoded();
        String kid = UUID.nameUUIDFromBytes(publicKeyBytes).toString();  // Use a UUID or hash as the kid

        return kid;
    }

    public static PublicKey getPublicKeyFromPrivateKey(PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(rsaPrivateKey.getModulus(), rsaPrivateKey.getPrivateExponent());
        return keyFactory.generatePublic(keySpec);
    }

    public static void main(String[] args) throws Exception {
        String kid = getKidFromPrivateKey();
        System.out.println("Key ID (kid): " + kid);
    }
}

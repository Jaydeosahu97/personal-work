package com.pay10ae.Fapi2.__client.config;

import com.pay10ae.Fapi2.__client.utill.KeyUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Configuration
public class RestTemplateConfig {

    @Value("${server.ssl.key-store-password}")
    private String keyStorePassword;

    @Value("${server.ssl.trust-store-password}")
    private String trustStorePassword;


    @Bean
    public RestTemplate restTemplate() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");

            // Load client certificate and private key
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            char[] keyStorePasswordArray = keyStorePassword.toCharArray();
            Resource keyStoreResource = new ClassPathResource("keystore.p12");
            URL keyStoreUrl = keyStoreResource.getURL();
            if (keyStoreUrl == null) {
                throw new FileNotFoundException("Keystore file not found on classpath");
            }
            keyStore.load(keyStoreUrl.openStream(), keyStorePasswordArray);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keyStorePasswordArray);

            // Load trust store
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            char[] trustStorePasswordArray = trustStorePassword.toCharArray();
            Resource trustStoreResource = new ClassPathResource("truststore.p12");
            URL trustStoreUrl = trustStoreResource.getURL();
            if (trustStoreUrl == null) {
                throw new FileNotFoundException("Truststore file not found on classpath");
            }
            trustStore.load(trustStoreUrl.openStream(), trustStorePasswordArray);

            // Initialize TrustManagerFactory with the trust store
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);
            // Initialize SSL context
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

            return new RestTemplate(new CustomRequestFactory(sslContext));
        } catch (NoSuchAlgorithmException | UnrecoverableKeyException | CertificateException | KeyStoreException |
                 IOException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    // Other configurations...
    private static class CustomRequestFactory extends SimpleClientHttpRequestFactory {

        private final SSLContext sslContext;

        public CustomRequestFactory(SSLContext sslContext) {
            this.sslContext = sslContext;
        }

        @Override
        protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
            if (connection instanceof HttpsURLConnection) {
                ((HttpsURLConnection) connection).setSSLSocketFactory(sslContext.getSocketFactory());
                //((javax.net.ssl.HttpsURLConnection) connection).setHostnameVerifier((hostname, session) -> true);// In a secure production environment, hostname verification should be enabled to ensure that the server being accessed is the intended one and to prevent potential security vulnerabilities
            }
            super.prepareConnection(connection, httpMethod);
        }
    }

    private static SSLContext getSSLContext() {
        try {
            // Load certificate and private key
//            String certificatePath = "/src/main/resources/server_cert.pem";
            String privateKeyPath = "";
            String keystorePassword = "keystore_password";
            ClassPathResource certificateResource = new ClassPathResource("server_cert.pem");
            // Load certificate
            InputStream certificateInputStream = certificateResource.getInputStream();
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) certificateFactory
                    .generateCertificate(certificateInputStream);

            // Load private key
//            byte[] privateKeyBytes = Base64.getEncoder().encodeToString(KeyUtils.getPrivateKey().getEncoded()).getBytes();
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
//            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            PrivateKey privateKey = KeyUtils.getPrivateKey();

            // Create keystore and add certificate and private key
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(null, null);
            keyStore.setCertificateEntry("certificate", certificate);
            keyStore.setKeyEntry("private-key", privateKey, keystorePassword.toCharArray(),
                    new java.security.cert.Certificate[]{certificate});

            // Create key manager factory
            KeyManagerFactory keyManagerFactory = KeyManagerFactory
                    .getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keystorePassword.toCharArray());

            // Create trust manager factory to trust all certificates
            TrustManagerFactory trustManagerFactory = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);

            // Create SSL context
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            return sslContext;
        } catch (Exception ex) {
//            logger.error("@@@ Exceptions YES BANK Escrow ::: " + ex.getMessage(), ex);
            ex.printStackTrace();
            return null;
        }
    }

}

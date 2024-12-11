package com.pay10ae.Fapi2.__client.service;


import com.pay10ae.Fapi2.__client.constants.AppConstants;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.kms.KmsClient;
import software.amazon.awssdk.services.kms.model.DataKeyPairSpec;
import software.amazon.awssdk.services.kms.model.GenerateDataKeyPairRequest;
import software.amazon.awssdk.services.kms.model.GenerateDataKeyPairResponse;

import java.util.Base64;

public class KmsKeyPairFetchService {
    private final KmsClient kmsClient;

    public KmsKeyPairFetchService(KmsClient kmsClient) {
        try {
            this.kmsClient = KmsClient.builder().region(Region.ME_CENTRAL_1).credentialsProvider(StaticCredentialsProvider.create(AppConstants.AWSCREDS)).build();
        } catch (Exception e) {
            throw new RuntimeException("Error while getting kms client");
        }
    }

    public void fetchPublicKey() {


        GenerateDataKeyPairRequest generateDataKeyPairRequest = GenerateDataKeyPairRequest.builder()
                .keyId(AppConstants.KEY_ID)
                .keyPairSpec(DataKeyPairSpec.RSA_2048)
                .build();

        GenerateDataKeyPairResponse generateDataKeyPairResponse = kmsClient.generateDataKeyPair(generateDataKeyPairRequest);

        String publicKey = Base64.getEncoder().encodeToString(generateDataKeyPairResponse.publicKey().asByteArray());
        System.out.println("Generated Public Key: " + publicKey);
        String privateKey = Base64.getEncoder().encodeToString(generateDataKeyPairResponse.privateKeyPlaintext().asByteArray());
        System.out.println("Generated Private Key: " + privateKey);

    }
}

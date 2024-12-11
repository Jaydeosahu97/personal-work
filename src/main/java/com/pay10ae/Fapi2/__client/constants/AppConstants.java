package com.pay10ae.Fapi2.__client.constants;


import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

public class AppConstants {
    public static final AwsBasicCredentials AWSCREDS;
    
    @Value("${key-id}")
    public static String KEY_ID;

    @Value("${access-key}")
    private static String ACCESS_KEYS;

    @Value("${secret-key}")
    private static String SECRET_KEYS;


    static {
        AWSCREDS = AwsBasicCredentials.create(ACCESS_KEYS, SECRET_KEYS);
    }
}


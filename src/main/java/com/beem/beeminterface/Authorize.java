package com.beem.beeminterface;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Base64;

/**
 * An authorization class
 * It handles apiKey and secretKey on the fly.
 * ...
 * Define these keys into your application.properties
 * as BEEM_API_KEY and BEEM_SECRET_KEY respectively
 */
@Component
public class Authorize {
    private final Token token;

    public Authorize(
            @Value("${BEEM_API_KEY}") String accessKey,
            @Value("${BEEM_SECRET_KEY}") String secretKey
    ) {

        if (secretKey == null || accessKey == null) {
            throw new IllegalArgumentException("Both access and secret key must not be null");
        }

        token = new Token(accessKey, secretKey);
        Tokens.setToken(token);

    }

    public String getAccessToken() {
        return token.getAccessKey();
    }

    public String getSecretToken() {
        return token.getSecretKey();
    }

    public void setAccessToken(String accessToken) {
        if (accessToken == null) {
            throw new IllegalArgumentException("Access key must not be null");
        }
        token.setAccessKey(accessToken);
    }

    public void setSecretToken(String secretToken) {
        if (secretToken == null) {
            throw new IllegalArgumentException("Secret key must not be null");
        }
        token.setSecretKey(secretToken);
    }

    public String getSecretKey() {
        return token.getSecretKey();
    }

    public String getAccessKey() {
        return token.getAccessKey();
    }

    public static String getEncodedToken() {
        Token token = Tokens.getToken();
        String authString = token.getAccessKey() + ":" + token.getSecretKey();
        return Base64.getEncoder().encodeToString(authString.getBytes());
    }

    public static MultiValueMap<String, String> getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(getEncodedToken());
        return headers;
    }
}

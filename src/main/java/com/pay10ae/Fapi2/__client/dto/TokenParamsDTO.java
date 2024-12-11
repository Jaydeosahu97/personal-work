package com.pay10ae.Fapi2.__client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenParamsDTO {
    @JsonProperty("url")
    private String url;
    //    @JsonProperty("grant_type")
//    private String grantType;
//    private String scope;
    private String code;
    @JsonProperty("redirect_uri")
    private String redirectUri;
    @JsonProperty("code_verifier")
    private String codeVerifier;
    //    @JsonProperty("client_assertion_type")
//    private String clientAssertionType;
    @JsonProperty("client_assertion")
    private String clientAssertion;

    public TokenParamsDTO() {
    }

    public TokenParamsDTO(String url, String code, String redirectUri, String codeVerifier, String clientAssertion) {
        this.url = url;
        this.code = code;
        this.redirectUri = redirectUri;
        this.codeVerifier = codeVerifier;
        this.clientAssertion = clientAssertion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getCodeVerifier() {
        return codeVerifier;
    }

    public void setCodeVerifier(String codeVerifier) {
        this.codeVerifier = codeVerifier;
    }

    public String getClientAssertion() {
        return clientAssertion;
    }

    public void setClientAssertion(String clientAssertion) {
        this.clientAssertion = clientAssertion;
    }

    @Override
    public String toString() {
        return "TokenParamsDTO{" +
                "url='" + url + '\'' +
                ", code='" + code + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", codeVerifier='" + codeVerifier + '\'' +
                ", clientAssertion='" + clientAssertion + '\'' +
                '}';
    }
}

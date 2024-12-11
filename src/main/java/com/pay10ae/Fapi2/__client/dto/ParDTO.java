package com.pay10ae.Fapi2.__client.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Date;

public class ParDTO {
    private String iss;
    private Date iat;
    private String sub;
    private String aud;
    private Date exp;
    private String acr;
    private String txn;
    private String scope;
    @JsonProperty("redirect_uri")
    private String redirectUri;
    @JsonProperty("client_id")
    private String clientId;
    private String nonce;
    private String state;
    private Date nbf;
    @JsonProperty("response_type")
    private String responseType;
    @JsonProperty("code_challenge")
    private String codeChallenge;
    @JsonProperty("code_challenge_method")
    private String codeChallengeMethod;
    @JsonProperty("max_age")
    private int maxAge;
    private RarDTO[] authorization_details;
    private DebtorAccount debtorAccount;

    public ParDTO() {}

    public ParDTO(String iss, String sub, String aud, Date exp, String acr, String txn, RarDTO[] authorization_details, DebtorAccount debtorAccount) {
        this.iss = iss;
        this.sub = sub;
        this.aud = aud;
        this.exp = exp;
        this.acr = acr;
        this.txn = txn;
        this.authorization_details = authorization_details;
        this.debtorAccount = debtorAccount;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getAud() {
        return aud;
    }

    public void setAud(String aud) {
        this.aud = aud;
    }

    public Date getExp() {
        return exp;
    }

    public void setExp(Date exp) {
        this.exp = exp;
    }

    public String getAcr() {
        return acr;
    }

    public void setAcr(String acr) {
        this.acr = acr;
    }

    public String getTxn() {
        return txn;
    }

    public void setTxn(String txn) {
        this.txn = txn;
    }

    public RarDTO[] getAuthorization_details() {
        return authorization_details;
    }

    public void setAuthorization_details(RarDTO[] authorization_details) {
        this.authorization_details = authorization_details;
    }

    public DebtorAccount getDebtorAccount() {
        return debtorAccount;
    }

    public void setDebtorAccount(DebtorAccount debtorAccount) {
        this.debtorAccount = debtorAccount;
    }

    public Date getIat() {
        return iat;
    }

    public void setIat(Date iat) {
        this.iat = iat;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getNbf() {
        return nbf;
    }

    public void setNbf(Date nbf) {
        this.nbf = nbf;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getCodeChallenge() {
        return codeChallenge;
    }

    public void setCodeChallenge(String codeChallenge) {
        this.codeChallenge = codeChallenge;
    }

    public String getCodeChallengeMethod() {
        return codeChallengeMethod;
    }

    public void setCodeChallengeMethod(String codeChallengeMethod) {
        this.codeChallengeMethod = codeChallengeMethod;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public String toString() {
        return "{" +
                "iss='" + iss + '\'' +
                ", sub='" + sub + '\'' +
                ", aud='" + aud + '\'' +
                ", exp='" + exp + '\'' +
                ", acr='" + acr + '\'' +
                ", txn='" + txn + '\'' +
                ", authorization_details=" + Arrays.toString(authorization_details) +
                ", debtorAccount=" + debtorAccount +
                '}';
    }
}

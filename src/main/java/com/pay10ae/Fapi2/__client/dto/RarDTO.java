package com.pay10ae.Fapi2.__client.dto;


import java.io.Serializable;
import java.util.Arrays;

public class RarDTO implements Serializable {
    private String type;
    private Consent consent;
    private Subscription subscription;
    private String[] actions;
    private String[] locations;
    //prepare object
    private InstructedAmount instructedAmount;
    private String creditorName;
    private CreditorAccount creditorAccount;
    private String remittanceInformationUnstructured;

    public RarDTO() {
    }

    public RarDTO(String type, Consent consent, Subscription subscription, String[] actions, String[] locations, InstructedAmount instructedAmount, String creditorName, CreditorAccount creditorAccount, String remittanceInformationUnstructured) {
        this.type = type;
        this.consent = consent;
        this.subscription = subscription;
        this.actions = actions;
        this.locations = locations;
        this.instructedAmount = instructedAmount;
        this.creditorName = creditorName;
        this.creditorAccount = creditorAccount;
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getActions() {
        return actions;
    }

    public void setActions(String[] actions) {
        this.actions = actions;
    }

    public String[] getLocations() {
        return locations;
    }

    public void setLocations(String[] locations) {
        this.locations = locations;
    }

    public Object getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(InstructedAmount instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public Object getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(CreditorAccount creditorAccount) {
        this.creditorAccount = creditorAccount;
    }

    public String getRemittanceInformationUnstructured() {
        return remittanceInformationUnstructured;
    }

    public void setRemittanceInformationUnstructured(String remittanceInformationUnstructured) {
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
    }

    @Override
    public String toString() {
        return "{" +
                "type='" + type + '\'' +
                ", consent=" + consent +
                ", subscription=" + subscription +
                ", actions=" + Arrays.toString(actions) +
                ", locations=" + Arrays.toString(locations) +
                ", instructedAmount=" + instructedAmount +
                ", creditorName='" + creditorName + '\'' +
                ", creditorAccount=" + creditorAccount +
                ", remittanceInformationUnstructured='" + remittanceInformationUnstructured + '\'' +
                '}';
    }

    public Consent getConsent() {
        return consent;
    }

    public void setConsent(Consent consent) {
        this.consent = consent;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }
}

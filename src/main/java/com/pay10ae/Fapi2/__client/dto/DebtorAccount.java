package com.pay10ae.Fapi2.__client.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class DebtorAccount {
    private String iban;
    @JsonProperty("user_role")
    private String userRole;

    public DebtorAccount() {
    }

    public DebtorAccount(String iban, String userRole) {
        this.iban = iban;
        this.userRole = userRole;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "{" +
                "iban='" + iban + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }
}

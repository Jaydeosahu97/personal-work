package com.pay10ae.Fapi2.__client.dto;


import java.io.Serializable;

public class CreditorAccount implements Serializable {
    private String iban;

    public CreditorAccount() {
    }

    public CreditorAccount(String iban) {
        this.iban = iban;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public String toString() {
        return "{" +
                "iban='" + iban + '\'' +
                '}';
    }
}


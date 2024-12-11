package com.pay10ae.Fapi2.__client.dto;


import java.io.Serializable;

public class InstructedAmount implements Serializable {
    private Double amount;
    private String currency;

    public InstructedAmount() {
    }

    public InstructedAmount(Double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}


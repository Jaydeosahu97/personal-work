package com.pay10ae.Fapi2.__client.dto;


public class Behalf {

    private String TradingName;
    private String LegalName;
    private String IdentifierType;
    private String Identifier;

    public Behalf() {
    }

    public Behalf(String tradingName, String legalName, String identifierType, String identifier) {
        TradingName = tradingName;
        LegalName = legalName;
        IdentifierType = identifierType;
        Identifier = identifier;
    }

    public String getTradingName() {
        return TradingName;
    }
    public void setTradingName(String tradingName) {
        TradingName = tradingName;
    }
    public String getLegalName() {
        return LegalName;
    }
    public void setLegalName(String legalName) {
        LegalName = legalName;
    }
    public String getIdentifierType() {
        return IdentifierType;
    }
    public void setIdentifierType(String identifierType) {
        IdentifierType = identifierType;
    }
    public String getIdentifier() {
        return Identifier;
    }
    public void setIdentifier(String identifier) {
        Identifier = identifier;
    }

    @Override
    public String toString() {
        return "{" +
                "TradingName='" + TradingName + '\'' +
                ", LegalName='" + LegalName + '\'' +
                ", IdentifierType='" + IdentifierType + '\'' +
                ", Identifier='" + Identifier + '\'' +
                '}';
    }
}

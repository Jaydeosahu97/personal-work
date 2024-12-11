package com.pay10ae.Fapi2.__client.dto;


import java.io.Serializable;

public record Consent(String expirationDateTime, Behalf onBehalfOf, String consentId, String[] permissions,
                      OpenFinanceBilling OpenFinanceBilling) implements Serializable {
}

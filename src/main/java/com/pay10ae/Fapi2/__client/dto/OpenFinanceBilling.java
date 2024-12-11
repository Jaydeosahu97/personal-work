package com.pay10ae.Fapi2.__client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenFinanceBilling(
        @JsonProperty("UserType")
        String userType,
        @JsonProperty("Purpose")
        String purpose) {
}

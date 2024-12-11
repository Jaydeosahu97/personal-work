package com.pay10ae.Fapi2.__client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookDTO {
    @JsonProperty("Url")
    private String url;
    @JsonProperty("IsActive")
    private Boolean isActive;

    public WebhookDTO() {
    }

    public WebhookDTO(String url, Boolean isActive) {
        this.url = url;
        this.isActive = isActive;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "{" +
                "url='" + url + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}

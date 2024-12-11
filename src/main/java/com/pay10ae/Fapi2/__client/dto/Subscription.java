package com.pay10ae.Fapi2.__client.dto;


import java.io.Serializable;

public class Subscription implements Serializable {
    private WebhookDTO Webhook;

    public Subscription(WebhookDTO webhook) {
        Webhook = webhook;
    }

    public Subscription() {
    }

    public WebhookDTO getWebhook() {
        return Webhook;
    }

    public void setWebhook(WebhookDTO webhook) {
        Webhook = webhook;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "Webhook=" + Webhook +
                '}';
    }
}

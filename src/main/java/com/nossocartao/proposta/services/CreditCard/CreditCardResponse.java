package com.nossocartao.proposta.services.CreditCard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreditCardResponse {
    @JsonProperty("id")
    private String cardNumber;

    public CreditCardResponse() {}

    public CreditCardResponse(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}

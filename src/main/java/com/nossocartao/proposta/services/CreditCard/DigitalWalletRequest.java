package com.nossocartao.proposta.services.CreditCard;

public class DigitalWalletRequest {
    private String email;
    private String carteira;

    public DigitalWalletRequest(String email, String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }
}

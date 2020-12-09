package com.nossocartao.proposta.modules.CreditCard;

import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewDigitalWalletRequest {
    @Email
    @NotBlank
    private String email;

    @NotNull
    @Enumerated
    private DigitalWalletType type;

    public NewDigitalWalletRequest(@Email String email, @NotNull DigitalWalletType type) {
        this.email = email;
        this.type = type;
    }

    public DigitalWallet toModel(CreditCard creditCard) {
        return new DigitalWallet(this.email, this.type, creditCard);
    }
}

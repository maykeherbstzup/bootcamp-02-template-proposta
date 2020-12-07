package com.nossocartao.proposta.modules.CreditCard;

import javax.validation.constraints.NotBlank;

public class NewCreditCardBlockRequest {
    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    public NewCreditCardBlockRequest(@NotBlank String ip, @NotBlank String userAgent) {
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public CreditCardBlock toModel(CreditCard creditCard)  {
        return new CreditCardBlock(this.ip, this.userAgent, creditCard);
    }
}

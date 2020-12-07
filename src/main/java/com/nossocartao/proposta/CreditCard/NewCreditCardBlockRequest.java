package com.nossocartao.proposta.CreditCard;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

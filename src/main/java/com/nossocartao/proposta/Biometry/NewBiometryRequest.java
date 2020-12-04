package com.nossocartao.proposta.Biometry;

import com.nossocartao.proposta.CreditCard.CreditCard;
import com.nossocartao.proposta.shared.TransactionExecutor;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

public class NewBiometryRequest {
    @NotBlank
    private String biometry;

    public NewBiometryRequest() {};

    public NewBiometryRequest(@NotBlank String biometry) {
        this.biometry = biometry;
    }

    public Biometry toModel(EntityManager em, CreditCard creditCard) {
        return new Biometry(this.biometry, creditCard);
    }

    public String getBiometry() {
        return biometry;
    }
}

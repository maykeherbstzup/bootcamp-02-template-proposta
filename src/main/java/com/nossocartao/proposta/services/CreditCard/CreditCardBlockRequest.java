package com.nossocartao.proposta.services.CreditCard;

public class CreditCardBlockRequest {
    private String sistemaResponsavel;

    public CreditCardBlockRequest(String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }
}

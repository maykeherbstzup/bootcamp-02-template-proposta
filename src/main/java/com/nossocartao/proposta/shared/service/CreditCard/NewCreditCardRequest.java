package com.nossocartao.proposta.shared.service.CreditCard;

import com.nossocartao.proposta.Proposal.Proposal;

import javax.validation.constraints.NotBlank;

public class NewCreditCardRequest {
    @NotBlank
    private String documento;

    @NotBlank
    private String nome;

    @NotBlank
    private String idProposta;

    public NewCreditCardRequest(@NotBlank String documento, @NotBlank String nome, @NotBlank String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public static NewCreditCardRequest fromModel(Proposal proposal) {
        NewCreditCardRequest newCreditCardRequest = new NewCreditCardRequest(proposal.getDocument(), proposal.getName(),
                proposal.getId());

        return newCreditCardRequest;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}

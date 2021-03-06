package com.nossocartao.proposta.services.FinancialAnalysis;

import com.nossocartao.proposta.modules.Proposal.Proposal;

import javax.validation.constraints.NotBlank;

public class FinancialAnalysisRequest {
    @NotBlank
    private String documento;

    @NotBlank
    private String nome;

    @NotBlank
    private String idProposta;

    public FinancialAnalysisRequest(@NotBlank String documento, @NotBlank String nome, @NotBlank String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public static FinancialAnalysisRequest fromModel(Proposal proposal) {
        FinancialAnalysisRequest financialAnalysisRequest = new FinancialAnalysisRequest(proposal.getDocument(), proposal.getName(),
                proposal.getId());

        return financialAnalysisRequest;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public void setIdProposta(String idProposta) {
        this.idProposta = idProposta;
    }
}

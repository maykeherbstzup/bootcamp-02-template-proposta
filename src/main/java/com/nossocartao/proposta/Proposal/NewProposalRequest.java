package com.nossocartao.proposta.Proposal;

import com.nossocartao.proposta.shared.validation.Document;
import com.nossocartao.proposta.shared.validation.Unique;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NewProposalRequest {
    @NotBlank
    @Document
    @Unique(entityClass = Proposal.class, fieldName = "document", errorMessage = "Já existe uma proposta em andamento" +
            " para" +
            " este documento")
    private String document;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    @Positive
    private BigDecimal salary;

    public NewProposalRequest(@NotBlank String document, @NotBlank @Email String email, @NotBlank String name,
                              @NotBlank String address, @NotNull @Positive BigDecimal salary) {
        System.out.println("Passou");
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public Proposal toModel() {
        Proposal proposal = new Proposal(this.document, this.email, this.name, this.address, this.salary);

        return proposal;
    }
}

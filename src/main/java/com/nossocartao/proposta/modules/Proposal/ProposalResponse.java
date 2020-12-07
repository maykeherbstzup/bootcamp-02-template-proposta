package com.nossocartao.proposta.modules.Proposal;

import java.math.BigDecimal;

public class ProposalResponse {
    private String document;
    private String email;
    private String name;
    private String address;
    private BigDecimal salary;
    private ProposalStatus status;

    public ProposalResponse(String document, String email, String name, String address, BigDecimal salary, ProposalStatus status) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
        this.status = status;
    }

    public static ProposalResponse fromModel(Proposal proposal) {
        return new ProposalResponse(proposal.getDocument(), proposal.getEmail(), proposal.getName(),
                proposal.getAddress(), proposal.getSalary(), proposal.getStatus());
    }

    public String getDocument() {
        return document;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public ProposalStatus getStatus() {
        return status;
    }
}

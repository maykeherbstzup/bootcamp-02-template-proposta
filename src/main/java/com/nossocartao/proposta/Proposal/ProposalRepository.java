package com.nossocartao.proposta.Proposal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, String> {
    List<Proposal> findByStatusAndCreditCardCardNumberNull(ProposalStatus status);
}
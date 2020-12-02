package com.nossocartao.proposta.Proposal;

import com.nossocartao.proposta.shared.service.FinancialAnalysis.FinancialAnalysisRequest;
import com.nossocartao.proposta.shared.service.FinancialAnalysis.FinancialAnalysisService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.persistence.EntityManager;
import java.net.URI;

@RestController
@RequestMapping("proposal")
public class ProposalController {
    @PersistenceContext
    EntityManager em;

    @Autowired
    FinancialAnalysisService financialAnalysisService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid NewProposalRequest newProposalRequest,
                                    UriComponentsBuilder UriBuilder) {
        Proposal proposal = newProposalRequest.toModel();

        em.persist(proposal);

        FinancialAnalysisRequest financialAnalysisRequest = FinancialAnalysisRequest.fromModel(proposal);

        if (this.financialAnalysisService.hasRestriction(financialAnalysisRequest)) {
            proposal.setStatus(ProposalStatus.NOT_ELIGIBLE);
        } else {
            proposal.setStatus(ProposalStatus.ELIGIBLE);
        }

        em.persist(proposal);

        URI locationURI = UriBuilder.path("proposal/{id}").buildAndExpand(proposal.getId()).toUri();

        return ResponseEntity.created(locationURI).build();
    }
}

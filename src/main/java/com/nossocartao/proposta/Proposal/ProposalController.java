package com.nossocartao.proposta.Proposal;

import com.nossocartao.proposta.shared.TransactionExecutor;
import com.nossocartao.proposta.shared.error.exception.ApiErrorException;
import com.nossocartao.proposta.shared.service.CreditCard.CreditCardService;
import com.nossocartao.proposta.shared.service.CreditCard.NewCreditCardRequest;
import com.nossocartao.proposta.shared.service.FinancialAnalysis.FinancialAnalysisRequest;
import com.nossocartao.proposta.shared.service.FinancialAnalysis.FinancialAnalysisService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.persistence.EntityManager;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("proposal")
public class ProposalController {
    @Autowired
    TransactionExecutor transactionExecutor;

    @Autowired
    FinancialAnalysisService financialAnalysisService;

    @Autowired
    ProposalRepository proposalRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid NewProposalRequest newProposalRequest,
                                    UriComponentsBuilder UriBuilder) {
        Proposal proposal = newProposalRequest.toModel();

        transactionExecutor.saveAndCommit(proposal);

        FinancialAnalysisRequest financialAnalysisRequest = FinancialAnalysisRequest.fromModel(proposal);

        if (this.financialAnalysisService.hasRestriction(financialAnalysisRequest)) {
            proposal.setStatus(ProposalStatus.NOT_ELIGIBLE);
        } else {
            proposal.setStatus(ProposalStatus.ELIGIBLE);
        }

        transactionExecutor.updateAndCommit(proposal);

        URI locationURI = UriBuilder.path("proposal/{id}").buildAndExpand(proposal.getId()).toUri();

        return ResponseEntity.created(locationURI).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProposalResponse> getProposal(@PathVariable(value = "id") String id) {
        Proposal proposal = this.proposalRepository.findById(id).orElseThrow(() -> new ApiErrorException("proposal",
                "Proposta não encontrada", HttpStatus.NOT_FOUND));

        ProposalResponse proposalResponse = ProposalResponse.fromModel(proposal);

        return ResponseEntity.ok(proposalResponse);
    }

}

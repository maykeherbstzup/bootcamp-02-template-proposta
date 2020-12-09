package com.nossocartao.proposta.modules.Proposal;

import com.nossocartao.proposta.shared.TransactionExecutor;
import com.nossocartao.proposta.shared.error.exception.ApiErrorException;
import com.nossocartao.proposta.services.FinancialAnalysis.FinancialAnalysisRequest;
import com.nossocartao.proposta.services.FinancialAnalysis.FinancialAnalysisService;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("proposal")
public class ProposalController {
    @Autowired
    TransactionExecutor transactionExecutor;

    @Autowired
    FinancialAnalysisService financialAnalysisService;

    @Autowired
    ProposalRepository proposalRepository;

    private Tracer tracer;

    public ProposalController(Tracer tracer) {
        this.tracer = tracer;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid NewProposalRequest newProposalRequest,
                                    UriComponentsBuilder UriBuilder) {
        Proposal proposal = newProposalRequest.toModel();

        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("user.email", proposal.getEmail());
        activeSpan.setBaggageItem("user.email", proposal.getEmail());
        activeSpan.log("user email " + proposal.getEmail());

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

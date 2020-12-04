package com.nossocartao.proposta.shared.service.CreditCard;

import com.nossocartao.proposta.CreditCard.CreditCard;
import com.nossocartao.proposta.Proposal.Proposal;
import com.nossocartao.proposta.Proposal.ProposalRepository;
import com.nossocartao.proposta.Proposal.ProposalStatus;
import com.nossocartao.proposta.shared.TransactionExecutor;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CreditCardService {
    @Autowired
    CreditCardInterface creditCardInterface;

    @Autowired
    ProposalRepository proposalRepository;

    @Autowired
    TransactionExecutor transactionExecutor;

    private final Logger LOGGER = LoggerFactory.getLogger(CreditCardService.class);

    @Scheduled(fixedDelay = 10000)
    public Map<String, Object> createCreditCards() {
        List<Proposal> proposals = this.proposalRepository.findByStatusAndCreditCardCardNumberNull(ProposalStatus.ELIGIBLE);

        proposals.stream().forEach((proposal) -> {
            NewCreditCardRequest newCreditCardRequest = NewCreditCardRequest.fromModel(proposal);

            try {
                ResponseEntity<?> response = this.creditCardInterface.createNewCreditCard(newCreditCardRequest);

                String[] path = response.getHeaders().getLocation().getPath().split("/");
                String creditCardNumber = path[path.length - 1];

                CreditCard creditCard = new CreditCard(creditCardNumber, proposal);

                transactionExecutor.saveAndCommit(creditCard);
            } catch (FeignException e) {
                LOGGER.error("Couldn't create new credit card for proposal: {}", proposal.getId(), e);
            }
        });

        return null;
    }
}

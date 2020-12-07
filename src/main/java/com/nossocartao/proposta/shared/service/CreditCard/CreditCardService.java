package com.nossocartao.proposta.shared.service.CreditCard;

import com.nossocartao.proposta.CreditCard.CreditCard;
import com.nossocartao.proposta.Proposal.Proposal;
import com.nossocartao.proposta.Proposal.ProposalRepository;
import com.nossocartao.proposta.Proposal.ProposalStatus;
import com.nossocartao.proposta.shared.TransactionExecutor;
import com.nossocartao.proposta.shared.error.exception.ApiErrorException;
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

    @Scheduled(fixedDelay = 300000)
    public Map<String, Object> createCreditCards() {
        List<Proposal> proposals = this.proposalRepository.findByStatusAndCreditCardCardNumberNull(ProposalStatus.ELIGIBLE);

        proposals.stream().forEach((proposal) -> {
            try {
                CreditCardResponse creditCardResponse = this.creditCardInterface.getCreditCardData(proposal.getId());

                CreditCard creditCard = new CreditCard(creditCardResponse.getCardNumber(), proposal);

                transactionExecutor.saveAndCommit(creditCard);
            } catch (FeignException e) {
                if (!(e instanceof FeignException.NotFound)) {
                    throw e;
                }
            }
        });

        return null;
    }
}

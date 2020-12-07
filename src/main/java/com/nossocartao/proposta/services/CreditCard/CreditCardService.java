package com.nossocartao.proposta.services.CreditCard;

import com.nossocartao.proposta.modules.CreditCard.CreditCard;
import com.nossocartao.proposta.modules.CreditCard.CreditCardStatus;
import com.nossocartao.proposta.modules.Proposal.Proposal;
import com.nossocartao.proposta.modules.Proposal.ProposalRepository;
import com.nossocartao.proposta.modules.Proposal.ProposalStatus;
import com.nossocartao.proposta.shared.TransactionExecutor;
import com.nossocartao.proposta.shared.error.exception.ApiErrorException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
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

    private static Logger logger = LoggerFactory.getLogger(CreditCardService.class);

    @Scheduled(fixedDelayString = "${credit_card.get_number.interval}")
    public Map<String, Object> getCreditCardNumber() {
        List<Proposal> proposals = this.proposalRepository.findByStatusAndCreditCardCardNumberNull(ProposalStatus.ELIGIBLE);

        proposals.stream().forEach((proposal) -> {
            try {
                CreditCardResponse creditCardResponse = this.creditCardInterface.getCreditCardData(proposal.getId());

                CreditCard creditCard = new CreditCard(creditCardResponse.getCardNumber(), proposal, CreditCardStatus.REGULAR);

                transactionExecutor.saveAndCommit(creditCard);
            } catch (FeignException e) {
                if (!(e instanceof FeignException.NotFound)) {
                    throw e;
                }
            }
        });

        return null;
    }

    public boolean blockCreditCard(String cardNumber) {
            try {
                Map<String, String> requestBody = new HashMap<>();
                requestBody.put("sistemaResponsavel", "teste");

                Map<String, String> result = this.creditCardInterface.blockCreditCard(cardNumber, requestBody);

                if (result != null && "BLOQUEADO".equals(result.get("resultado"))) {
                    return true;
                }

                return false;
            } catch (FeignException e) {
                logger.error("Couldn't block credit card");

                throw new ApiErrorException("creditCard", "Não foi possível bloquear o cartão, tente novamente mais" +
                        " tarde", HttpStatus.BAD_REQUEST);
            }
    }
}

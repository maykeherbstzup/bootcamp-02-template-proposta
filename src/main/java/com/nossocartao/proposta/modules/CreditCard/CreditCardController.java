package com.nossocartao.proposta.modules.CreditCard;

import com.nossocartao.proposta.services.CreditCard.CreditCardService;
import com.nossocartao.proposta.shared.TransactionExecutor;
import com.nossocartao.proposta.shared.error.exception.ApiErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("credit-card")
public class CreditCardController {
    @Autowired
    EntityManager em;

    @Autowired
    TransactionExecutor transactionExecutor;

    @Autowired
    CreditCardService creditCardService;

    @PostMapping("/{id}/biometry")
    public ResponseEntity<?> create(@PathVariable(value = "id") String id,
                                    @RequestBody @Valid NewBiometryRequest newBiometryRequest, UriComponentsBuilder UriBuilder) {
        CreditCard creditCard = em.find(CreditCard.class, id);

        if (creditCard == null) {
            throw new ApiErrorException("creditCardId", "Cartão não encontrado", HttpStatus.NOT_FOUND);
        }

        Biometry biometry = newBiometryRequest.toModel(em, creditCard);

        transactionExecutor.saveAndCommit(biometry);

        URI uri = UriBuilder.path("/biometry/{id}").buildAndExpand(biometry.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PostMapping("{id}/block")
    public ResponseEntity<?> block(@PathVariable(value = "id") String id,
                                   @RequestBody @Valid NewCreditCardBlockRequest newCreditCardBlockRequest) {
        CreditCard creditCard = em.find(CreditCard.class, id);

        if (creditCard == null) {
            throw new ApiErrorException("creditCardId", "Cartão não encontrado", HttpStatus.NOT_FOUND);
        }

        if (creditCard.isBlocked()) {
            throw new ApiErrorException("creditCardId", "Este cartão já está bloqueado", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        CreditCardBlock creditCardBlock = newCreditCardBlockRequest.toModel(creditCard);

        creditCardService.blockCreditCard(creditCard.getCardNumber());

        transactionExecutor.saveAndCommit(creditCardBlock);

        creditCard.setStatus(CreditCardStatus.BLOCKED);
        transactionExecutor.saveAndCommit(creditCard);

        return ResponseEntity.ok().build();
    }
}

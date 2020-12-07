package com.nossocartao.proposta.CreditCard;

import com.nossocartao.proposta.shared.error.exception.ApiErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("credit-card")
public class CreditCardController {
    @Autowired
    EntityManager em;

    @Transactional
    @PostMapping("{id}/block")
    public ResponseEntity<?> block(@PathVariable(value = "id") String id,
                                   @RequestBody @Valid NewCreditCardBlockRequest newCreditCardBlockRequest) {
        CreditCard creditCard = em.find(CreditCard.class, id);

        if (creditCard == null) {
            throw new ApiErrorException("id", "Cartão não encontrado", HttpStatus.NOT_FOUND);
        }

        if (creditCard.isBlocked()) {
            throw new ApiErrorException("id", "Este cartão já está bloqueado", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        CreditCardBlock creditCardBlock = newCreditCardBlockRequest.toModel(creditCard);

        em.persist(creditCardBlock);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @PostMapping("/{id}/biometry")
    public ResponseEntity<?> create(@PathVariable(value = "id") String id,
                                    @RequestBody @Valid NewBiometryRequest newBiometryRequest, UriComponentsBuilder UriBuilder) {
        CreditCard creditCard = em.find(CreditCard.class, id);

        if (creditCard == null) {
            throw new ApiErrorException("creditCardId", "Credit card not found", HttpStatus.NOT_FOUND);
        }

        Biometry biometry = newBiometryRequest.toModel(em, creditCard);

        em.persist(biometry);

        URI uri = UriBuilder.path("/biometry/{id}").buildAndExpand(biometry.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}

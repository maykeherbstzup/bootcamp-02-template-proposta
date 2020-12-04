package com.nossocartao.proposta.Biometry;

import com.nossocartao.proposta.CreditCard.CreditCard;
import com.nossocartao.proposta.shared.TransactionExecutor;
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
import java.util.Optional;

@RestController
@RequestMapping("/biometry")
public class BiometryController {
    @Autowired
    EntityManager em;

    @Transactional
    @PostMapping("/{creditCardId}")
    public ResponseEntity<?> create(@PathVariable(value = "creditCardId") String creditCardId,
                                    @RequestBody @Valid NewBiometryRequest newBiometryRequest, UriComponentsBuilder UriBuilder) {
        CreditCard creditCard = em.find(CreditCard.class, creditCardId);

        if (creditCard == null) {
            throw new ApiErrorException("creditCardId", "Credit card not found", HttpStatus.NOT_FOUND);
        }

        Biometry biometry = newBiometryRequest.toModel(em, creditCard);

        em.persist(biometry);

        URI uri = UriBuilder.path("/biometry/{id}").buildAndExpand(biometry.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}

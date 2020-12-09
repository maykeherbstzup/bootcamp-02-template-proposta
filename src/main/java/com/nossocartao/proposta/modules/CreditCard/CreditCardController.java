package com.nossocartao.proposta.modules.CreditCard;

import com.nossocartao.proposta.services.CreditCard.CreditCardService;
import com.nossocartao.proposta.services.CreditCard.DigitalWalletRequest;
import com.nossocartao.proposta.services.CreditCard.TravelNoticeRequest;
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
import java.util.List;

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

    @PostMapping("/{id}/travel-notice")
    public ResponseEntity<?> travelNotice(@PathVariable(value = "id") String id,
                                          @RequestBody @Valid NewTravelNoticeRequest newTravelNoticeRequest) {
        CreditCard creditCard = em.find(CreditCard.class, id);

        if (creditCard == null) {
            throw new ApiErrorException("creditCardId", "Cartão não encontrado", HttpStatus.NOT_FOUND);
        }

        TravelNotice travelNotice = newTravelNoticeRequest.toModel(creditCard);

        TravelNoticeRequest travelNoticeRequest = new TravelNoticeRequest(travelNotice.getDestination(),
                travelNotice.getEndDate().toString());

        this.creditCardService.sendTravelNotice(creditCard.getCardNumber(), travelNoticeRequest);

        transactionExecutor.saveAndCommit(travelNotice);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/digital-wallet")
    public ResponseEntity<?> associateDigitalWallet(@PathVariable(value = "id") String id,
                                          @RequestBody @Valid NewDigitalWalletRequest newDigitalWalletRequest) {
        CreditCard creditCard = em.find(CreditCard.class, id);

        if (creditCard == null) {
            throw new ApiErrorException("creditCardId", "Cartão não encontrado", HttpStatus.NOT_FOUND);
        }

        List<DigitalWallet> cardDigitalWallets = creditCard.getDigitalWallet();

        DigitalWallet digitalWallet = newDigitalWalletRequest.toModel(creditCard);

        cardDigitalWallets.stream().forEach(wallet -> {
            if (wallet.getType().equals(digitalWallet.getType())) {
                throw new ApiErrorException("type", "Cartão já associado a esta carteira digital",
                        HttpStatus.UNPROCESSABLE_ENTITY);
            }
        });

        DigitalWalletRequest digitalWalletRequest = new DigitalWalletRequest(digitalWallet.getEmail(),
                digitalWallet.getType().getValue());

        String walletId = this.creditCardService.associateDigitalWallet(creditCard.getCardNumber(),
                digitalWalletRequest);

        digitalWallet.setWalletId(walletId);

        transactionExecutor.saveAndCommit(digitalWallet);

        return ResponseEntity.ok().build();
    }
}

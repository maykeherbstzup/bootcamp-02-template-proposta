package com.nossocartao.proposta.shared.service.CreditCard;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "CreditCard", url = "http://localhost:8888/api")
public interface CreditCardInterface {
    @GetMapping(path = "/cartoes/{id}", consumes = "application/json", produces = "application/json")
    CreditCardResponse getCreditCardData(@RequestParam(name="idProposta") String proposalId);
}

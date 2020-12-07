package com.nossocartao.proposta.services.CreditCard;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "CreditCard", url = "${credit_card.url}")
public interface CreditCardInterface {
    @GetMapping(path = "/api/cartoes")
    CreditCardResponse getCreditCardData(@RequestParam(name="idProposta") String proposalId);

    @PostMapping(path = "/api/cartoes/{id}/bloqueios")
    Map<String, String> blockCreditCard(@PathVariable(name="id") String creditCardNumber,
                                        @RequestBody Map<String, String>blockRequest);
}

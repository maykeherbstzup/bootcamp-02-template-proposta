package com.nossocartao.proposta.shared.service.CreditCard;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "CreditCard", url = "http://localhost:8888/api")
public interface CreditCardInterface {
    @RequestMapping(method = RequestMethod.POST, value = "/cartoes")
    ResponseEntity<?> createNewCreditCard(@RequestBody NewCreditCardRequest newCreditCardRequest);
}

package com.nossocartao.proposta.shared.service.FinancialAnalysis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "AnalysisRequest", url = "http://localhost:9999/api")
public interface FinancialAnalysisInterface {
    @RequestMapping(method = RequestMethod.POST, value = "/solicitacao")
    Map<String, String> getAnalysis(@RequestBody FinancialAnalysisRequest analysisRequestDTO);
}

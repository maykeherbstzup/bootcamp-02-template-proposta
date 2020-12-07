package com.nossocartao.proposta.services.FinancialAnalysis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "FinancialAnalysis", url = "${financial_analysis.url}")
public interface FinancialAnalysisInterface {
    @RequestMapping(method = RequestMethod.POST, value = "/api/solicitacao")
    Map<String, String> getAnalysis(@RequestBody FinancialAnalysisRequest analysisRequestDTO);
}

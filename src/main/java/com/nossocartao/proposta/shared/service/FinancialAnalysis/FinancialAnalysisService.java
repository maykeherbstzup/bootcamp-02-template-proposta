package com.nossocartao.proposta.shared.service.FinancialAnalysis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nossocartao.proposta.shared.error.exception.ApiErrorException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FinancialAnalysisService {
    @Autowired
    FinancialAnalysisInterface financialAnalysisInterface;

    public boolean hasRestriction(FinancialAnalysisRequest financialAnalysisRequest) {
        Map<String, String> analysisResult = this.getAnalysis(financialAnalysisRequest);

        if ("SEM_RESTRICAO".equalsIgnoreCase(analysisResult.get("resultadoSolicitacao"))) {
            return false;
        }

        return true;
    }

    private Map<String, String> getAnalysis(FinancialAnalysisRequest financialAnalysisRequest) {
        Map<String, String> result;

        try {
            result = this.financialAnalysisInterface.getAnalysis(financialAnalysisRequest);
        } catch (FeignException.UnprocessableEntity e) {
            try {
                result = new ObjectMapper().readValue(e.contentUTF8(), HashMap.class);
            } catch (JsonProcessingException ex){
                throw new ApiErrorException("financialAnalysis", "Não foi possível realizar a análise financeira, " +
                        "tente novamente mais tarde", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return result;
    }
}

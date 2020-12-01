package com.nossocartao.proposta.shared.error.exception;

import org.springframework.http.HttpStatus;

public class ApiErrorException extends RuntimeException {
    private HttpStatus httpStatus;
    private String fieldName;
    private String errorMessage;

    public ApiErrorException(String fieldName, String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);

        this.httpStatus = httpStatus;
        this.fieldName = fieldName;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getFieldName() {
        return fieldName;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

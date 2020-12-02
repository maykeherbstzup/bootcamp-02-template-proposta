package com.nossocartao.proposta.shared.error.exception;

import org.springframework.http.HttpStatus;

public class ApiErrorException extends RuntimeException {
    private HttpStatus httpStatus;
    private String id;
    private String errorMessage;

    public ApiErrorException(String id, String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);

        this.httpStatus = httpStatus;
        this.id = id;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getId() {
        return id;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

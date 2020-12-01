package com.nossocartao.proposta.shared.error;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
    private Map<String, String> messages = new HashMap<>();

    public ErrorResponse() {
    }

    public ErrorResponse(Map<String, String> messages) {
        this.messages = messages;
    }

    public void addMessage(String fieldName, String message) {
        this.messages.put(fieldName, message);
    }

    public Map<String, String> getMessages() {
        return messages;
    }
}

package com.nossocartao.proposta.services.CreditCard;

public class TravelNoticeRequest {
    private String destino;

    private String validoAte;

    public TravelNoticeRequest(String destino, String validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public String getValidoAte() {
        return validoAte;
    }
}

package com.nossocartao.proposta.modules.CreditCard;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class NewTravelNoticeRequest {
    @NotBlank
    private String destination;

    @NotNull
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate endDate;

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    public NewTravelNoticeRequest(@NotBlank String destination, @NotBlank String ip, @NotBlank String userAgent) {
        this.destination = destination;
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public TravelNotice toModel(CreditCard creditCard) {
        return new TravelNotice(this.destination, this.endDate, this.ip, this.userAgent, creditCard);
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

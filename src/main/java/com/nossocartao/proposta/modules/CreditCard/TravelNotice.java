package com.nossocartao.proposta.modules.CreditCard;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "travel_notice")
public class TravelNotice {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    private String destination;

    @NotNull
    @Future
    private LocalDate endDate;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull
    private String ip;

    @NotNull
    private String userAgent;

    @NotNull
    @ManyToOne
    CreditCard creditCard;

    public TravelNotice(@NotNull String destination, @NotNull @Future LocalDate endDate, @NotNull String ip,
                        @NotNull String userAgent, @NotNull CreditCard creditCard) {
        this.destination = destination;
        this.endDate = endDate;
        this.ip = ip;
        this.userAgent = userAgent;
        this.creditCard = creditCard;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}

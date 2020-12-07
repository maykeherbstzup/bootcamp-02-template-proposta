package com.nossocartao.proposta.CreditCard;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "credit_card_block")
public class CreditCardBlock {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    private LocalDateTime blockedAt = LocalDateTime.now();

    @NotNull
    private String ip;

    @NotNull
    private String userAgent;

    @NotNull
    @OneToOne
    private CreditCard creditCard;

    @Deprecated
    private CreditCardBlock() {
    }

    public CreditCardBlock(@NotBlank String ip, @NotBlank String userAgent, @NotNull CreditCard creditCard) {
        Assert.hasText(ip, "ip shouldn't be blank");
        Assert.hasText(userAgent, "user agent shouldn't be blank");
        Assert.notNull(creditCard, "credit card shouldn't be null");

        this.ip = ip;
        this.userAgent = userAgent;
        this.creditCard = creditCard;
    }
}

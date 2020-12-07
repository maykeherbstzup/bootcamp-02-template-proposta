package com.nossocartao.proposta.modules.CreditCard;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "biometry", uniqueConstraints = @UniqueConstraint(name = "unique_biometry", columnNames = {"biometry",
        "credit_card_id"}))
public class Biometry {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    private String biometry;

    @NotNull
    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull
    @OneToOne
    private CreditCard creditCard;

    @Deprecated
    public Biometry() {}

    public Biometry(@NotBlank String biometry, CreditCard creditCard) {
        Assert.hasText(biometry, "Biometry shouldn't be null");
        Assert.notNull(creditCard, "Credit card shouldn't be null");

        this.biometry = biometry;
        this.creditCard = creditCard;
    }

    public String getId() {
        return id;
    }
}

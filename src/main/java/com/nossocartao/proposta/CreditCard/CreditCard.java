package com.nossocartao.proposta.CreditCard;

import com.nossocartao.proposta.Proposal.Proposal;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "credit_card", uniqueConstraints = @UniqueConstraint(name = "unique_card_number", columnNames =
        "cardNumber"))
public class CreditCard {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name= "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    private String cardNumber;

    @NotNull
    @OneToOne
    private Proposal proposal;

    @Deprecated
    public CreditCard() {}

    public CreditCard(@NotBlank String cardNumber, @NotBlank Proposal proposal) {
        Assert.hasText(cardNumber, "Card number shouldn't be blank");
        Assert.notNull(proposal, "Proposal shouldn't be null");

        this.cardNumber = cardNumber;
        this.proposal = proposal;
    }
}
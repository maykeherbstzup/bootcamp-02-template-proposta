package com.nossocartao.proposta.modules.CreditCard;

import com.nossocartao.proposta.modules.Proposal.Proposal;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @Enumerated
    @NotNull
    private CreditCardStatus status;

    @OneToOne(mappedBy = "creditCard")
    private CreditCardBlock creditCardBlock;

    @OneToMany(mappedBy = "creditCard")
    private List<TravelNotice> travelNotice;

    @Deprecated
    public CreditCard() {}

    public CreditCard(@NotBlank String cardNumber, @NotBlank Proposal proposal, @NotNull CreditCardStatus status) {
        Assert.hasText(cardNumber, "Card number shouldn't be blank");
        Assert.notNull(proposal, "Proposal shouldn't be null");
        Assert.notNull(status, "Status shouldn't be null");

        this.cardNumber = cardNumber;
        this.proposal = proposal;
        this.status = status;
    }

    public boolean isBlocked() {
        return this.creditCardBlock != null;
    }

    public void setStatus(CreditCardStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
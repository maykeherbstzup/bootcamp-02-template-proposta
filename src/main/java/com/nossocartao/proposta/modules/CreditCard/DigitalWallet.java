package com.nossocartao.proposta.modules.CreditCard;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "digital_wallet")
public class DigitalWallet {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotBlank
    private String walletId;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Enumerated
    private DigitalWalletType type;

    @NotNull
    @ManyToOne
    CreditCard creditCard;

    @Deprecated
    public DigitalWallet() {}

    public DigitalWallet(@NotBlank @Email String email, @NotNull DigitalWalletType type, @NotNull CreditCard creditCard) {
        this.email = email;
        this.type = type;
        this.creditCard = creditCard;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getEmail() {
        return email;
    }

    public DigitalWalletType getType() {
        return type;
    }
}

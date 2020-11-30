package com.nossocartao.proposta.Proposal;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "proposal")
public class Proposal {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    private String document;

    @NotNull
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String address;

    @NotNull
    @Positive
    private BigDecimal salary;

    @Deprecated
    public Proposal() {};

    public Proposal(@NotNull String document, @NotNull String email, @NotNull String name, @NotNull String address, @NotNull @Positive BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }
}

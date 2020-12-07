package com.nossocartao.proposta.modules.Proposal;

import com.nossocartao.proposta.modules.CreditCard.CreditCard;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(
        name = "proposal",
        uniqueConstraints = {@UniqueConstraint(name = "unique_document", columnNames = "document")}
)
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

    @Enumerated
    private ProposalStatus status;

    @OneToOne(mappedBy = "proposal")
    private CreditCard creditCard;

    @Deprecated
    public Proposal() {
    }

    public Proposal(@NotNull String document, @NotNull String email, @NotNull String name, @NotNull String address, @NotNull @Positive BigDecimal salary) {
        Assert.hasText(document, "Document is required");
        Assert.hasText(email, "Email is required");
        Assert.hasText(name, "Name is required");
        Assert.hasText(address, "Address is required");
        Assert.notNull(salary, "Salary is required");
        Assert.isTrue(salary.compareTo(BigDecimal.ZERO) == 1, "Salary should be greater than zero");

        this.document = this.sanitizeDocument(document);
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    private String sanitizeDocument(String document) {
        return document.replace(".", "").replace("-", "").replace("/", "");
    }

    public String getId() {
        return id;
    }

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getEmail() {
        return email;
    }

    public ProposalStatus getStatus() {
        return status;
    }

    public void setStatus(ProposalStatus status) {
        this.status = status;
    }
}

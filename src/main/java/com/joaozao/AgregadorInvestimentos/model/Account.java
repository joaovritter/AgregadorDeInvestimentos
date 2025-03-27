package com.joaozao.AgregadorInvestimentos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter //lombok getter
@Setter //lombok setter
@Entity
@Table (name ="tb_accounts")
public class Account {
    @Id
    @Column (name = "account_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountId;

    private String description;

    @ManyToOne //varias contas pertencem a um usuario
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL) //uma account tem um billing address
    private BillingAddress billingAddress;

    @OneToMany (mappedBy = "account")
    private List<AccountStock> accountStock;

    public Account() {

    }

    public Account(UUID accountId, User user, String description,BillingAddress billingAddress, List<AccountStock> accountStock) {
        this.accountId = accountId;
        this.description = description;
        this.user = user;
        this.billingAddress = billingAddress;
        this.accountStock = accountStock;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    public List<AccountStock> getAccountStock() {
        return accountStock;
    }

    public void setAccountStock(List<AccountStock> accountStock) {
        this.accountStock = accountStock;
    }
}

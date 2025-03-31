package com.joaozao.AgregadorInvestimentos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter //lombok getter
@Setter //lombok setter
@Entity
@Table (name ="tb_accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column (name = "account_id")
    private UUID accountId;

    @ManyToOne //varias contas pertencem a um usuario
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL) //uma account tem um billing address
    @PrimaryKeyJoinColumn
    private BillingAddress billingAddress;

    @Column(name = "description")
    private String description;

    @OneToMany (mappedBy = "account")
    private List<AccountStock> accountStock = new ArrayList<>();

    public Account() {

    }

    public Account(UUID accountId, User user, BillingAddress billingAddress, String description, List<AccountStock> accountStock) {
        this.accountId = accountId;
        this.user = user;
        this.billingAddress = billingAddress;
        this.description = description;
        this.accountStock = accountStock;
    }
}

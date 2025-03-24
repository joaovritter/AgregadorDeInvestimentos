package com.joaozao.AgregadorInvestimentos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter //lombok getter
@Setter //lombok setter
@Entity
@Table (name ="tb_accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID accountId;
    private String description;

    @ManyToOne //varias contas pertencem a um usuario
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn //passa a pk da account para a billingaddress
    private BillingAddress billingAddress;

    public Account() {

    }
    public Account(UUID accountId, String description) {
        this.accountId = accountId;
        this.description = description;
    }
}

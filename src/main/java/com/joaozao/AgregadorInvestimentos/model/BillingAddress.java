package com.joaozao.AgregadorInvestimentos.model;

import com.joaozao.AgregadorInvestimentos.controller.AccountController;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_billing_address")
public class BillingAddress {
    @Id
    @Column (name = "account_id")
    private UUID id;

    @OneToOne (cascade = CascadeType.ALL)//um billing address tem uma account
    @MapsId //pega id da entidade account e salva como id da entidade BillingAddress
    @JoinColumn (name = "account_id")
    private Account account;

    @Column (name = "street")
    private String street;

    @Column (name = "number")
    private Integer number;

    public BillingAddress() {
    }

    public BillingAddress(UUID id, Account account, String street, Integer number) {
        this.id = id;
        this.account = account;
        this.street = street;
        this.number = number;
    }
}

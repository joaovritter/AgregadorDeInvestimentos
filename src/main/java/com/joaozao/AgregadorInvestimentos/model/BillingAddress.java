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

    private String street;
    private int number;


    @OneToOne (cascade = CascadeType.ALL)//um billing address tem uma account
    @MapsId //pega id da entidade account e salva como id da entidade BillingAddress
    @JoinColumn (name = "account_id")
    private Account account;

    public BillingAddress() {
    }

    public BillingAddress(UUID id, String street, int number, Account account) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.account = account;
    }
}

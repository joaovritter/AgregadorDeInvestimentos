package com.joaozao.AgregadorInvestimentos.model;

import com.joaozao.AgregadorInvestimentos.controller.AccountController;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_billingaddress")
public class BillingAddress {

    @Id
    @Column (name = "account_id")
    private UUID id;

    @OneToOne
    @MapsId //pega id da entidade account e salva como id da entidade BillingAddress
    @JoinColumn (name = "account_id")
    private Account account;
    private String street;
    private String number;

    public BillingAddress() {

    }

    public BillingAddress(Account account, String street, String number) {
        this.account = account;
        this.street = street;
        this.number = number;
    }
}

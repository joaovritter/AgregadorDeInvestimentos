package com.joaozao.AgregadorInvestimentos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@Entity
@Table (name = "tb_stok")
public class Stock {
    @Id
    @Column (name = "stock_id")
    private String stockId;

    //@OneToMany (mappedBy = "stock")
    //private List<AccountStock> accountStock;

    private String description;

   public Stock (){

   }
    public Stock(String stockId, /*List<AccountStock> accountStock,*/ String description) {
        this.stockId = stockId;
        //this.accountStock = accountStock;
        this.description = description;
    }
}

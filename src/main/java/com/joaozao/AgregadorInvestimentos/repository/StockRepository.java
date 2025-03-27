package com.joaozao.AgregadorInvestimentos.repository;

import com.joaozao.AgregadorInvestimentos.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StockRepository extends JpaRepository<Stock, String> {
}

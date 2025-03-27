package com.joaozao.AgregadorInvestimentos.repository;

import com.joaozao.AgregadorInvestimentos.model.AccountStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountStockRepository extends JpaRepository<AccountStock, UUID> {
}

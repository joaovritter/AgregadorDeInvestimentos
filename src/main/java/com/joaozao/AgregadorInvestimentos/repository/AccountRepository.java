package com.joaozao.AgregadorInvestimentos.repository;

import com.joaozao.AgregadorInvestimentos.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}

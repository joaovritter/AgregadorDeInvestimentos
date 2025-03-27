package com.joaozao.AgregadorInvestimentos.repository;

import com.joaozao.AgregadorInvestimentos.model.BillingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BillingAddressRepository extends JpaRepository<BillingAddress, UUID> {
}

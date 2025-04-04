package com.joaozao.AgregadorInvestimentos.controller;

import com.joaozao.AgregadorInvestimentos.dto.AccountStockResponseDto;
import com.joaozao.AgregadorInvestimentos.dto.AssociateAccountStockDto;
import com.joaozao.AgregadorInvestimentos.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/stocks/{accountId}")
    public ResponseEntity<Void> associateAccountStock(@PathVariable ("accountId")String accountId, @RequestBody AssociateAccountStockDto dto) {
        accountService.associateStock(accountId, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stocks/{accountId}")
    public ResponseEntity<List<AccountStockResponseDto>> listAccountStock(@PathVariable ("accountId")String accountId) {
        var stocks = accountService.listStocks(accountId);
        return ResponseEntity.ok(stocks);
    }

}

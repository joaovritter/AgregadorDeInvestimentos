package com.joaozao.AgregadorInvestimentos.service;

import com.joaozao.AgregadorInvestimentos.dto.AccountStockResponseDto;
import com.joaozao.AgregadorInvestimentos.dto.AssociateAccountStockDto;
import com.joaozao.AgregadorInvestimentos.model.AccountStock;
import com.joaozao.AgregadorInvestimentos.model.AccountStockId;
import com.joaozao.AgregadorInvestimentos.repository.AccountRepository;
import com.joaozao.AgregadorInvestimentos.repository.AccountStockRepository;
import com.joaozao.AgregadorInvestimentos.repository.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    private AccountRepository accountRepository;
    private StockRepository stockRepository;
    private AccountStockRepository accountStockRepository;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
    }


    public void associateStock(String accountId, AssociateAccountStockDto dto) {
        var account = accountRepository.findById(UUID.fromString(accountId)) //se existe pega a conta
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(dto.stockId()) //se existe pega o stock
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //DTO -> entity
        var id = new AccountStockId(account.getAccountId(), stock.getStockId()); //pegamos o id atraves da classe AccountStockId (por ser um id Composto)
        var entity = new AccountStock(
                id,
                account,
                stock,
                dto.quantity()
        );
        accountStockRepository.save(entity);


    }

    public List<AccountStockResponseDto> listStocks(String accountId) {
        var account = accountRepository.findById(UUID.fromString(accountId)) //se existe pega a conta
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return account.getAccountStock()
                .stream()
                .map(as -> new AccountStockResponseDto(as.getStock().getStockId(), as.getQuantity(), 0.0))
                .toList();
    }
}

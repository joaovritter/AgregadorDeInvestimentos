package com.joaozao.AgregadorInvestimentos.service;

import com.joaozao.AgregadorInvestimentos.dto.CreateStockDto;
import com.joaozao.AgregadorInvestimentos.model.Stock;
import com.joaozao.AgregadorInvestimentos.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDto) {
        //Dto -> Entity
        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );
        stockRepository.save(stock);
    }
}

package com.jpmorgan.simplestock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static java.math.BigDecimal.valueOf;
import com.jpmorgan.simplestock.service.StockService;
import com.jpmorgan.simplestock.service.StockServiceImpl;

/**
 * 
 * @author Ruchita Surve main class
 */
public class SimpleStockApp {
	public static void main(String[] args) {
		BigDecimal MARKET_PRICE = valueOf(100);
		StockService stockService = new StockServiceImpl();
		List<String> stockSymbols = new ArrayList<String>();
		stockSymbols.add("TEA");
		stockSymbols.add("POP");
		stockSymbols.add("ALE");
		stockSymbols.add("GIN");
		stockSymbols.add("JOE");

		for (String stockSymbol : stockSymbols) {
			stockService.setStock(stockSymbol);
			System.out.println("*****" + stockSymbol + "***********");
			System.out.println("Divident Yield : " + stockService.calculateDividendYield(MARKET_PRICE));
			System.out.println("PE ratio :" + stockService.calculatePEratio(MARKET_PRICE));
			stockService.recordTrade();
			System.out.println("Volume weighted stock price :" + stockService.calculateVolumeWeightedStockPrice());
		}
		System.out.println("GBCE all share index " + stockService.calculateGBCEAllShareIndex());

	}
}

package com.jpmorgan.simplestock.dto;

import com.jpmorgan.simplestock.entity.Stock;

public interface StockDao {

	public Stock getStockBySymbol(String stockSymbol);
}

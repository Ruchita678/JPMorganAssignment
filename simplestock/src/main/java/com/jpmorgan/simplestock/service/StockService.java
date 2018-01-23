package com.jpmorgan.simplestock.service;

import java.math.BigDecimal;

/**
 * 
 * @author Ruchita Surve
 * 
 *         Interface StockService
 *
 */
public interface StockService {

	/**
	 * 
	 * @param stockSymbol
	 * 
	 *            Initializes stock by fetching details from db for a given
	 *            stock name
	 */
	public void setStock(String stockSymbol);

	/**
	 * 
	 * @param marketPrice
	 * @return Dividend yield Dividend Yield for a given stock
	 */
	public BigDecimal calculateDividendYield(BigDecimal marketPrice);

	/**
	 * 
	 * @param marketPrice
	 * @return PEratio for a given stock
	 */
	public BigDecimal calculatePEratio(BigDecimal marketPrice);

	/**
	 * Records trade (BUY and SELL) for each stock
	 */
	public void recordTrade();

	/**
	 * Calculates calculateVolumeWeightedStockPrice for a given stock
	 * 
	 * @return volume Weighted StockPrice
	 */
	public BigDecimal calculateVolumeWeightedStockPrice();

	/**
	 * calculates GBCE All share index for all stocks based on geometric mean of
	 * prices of all stocks
	 * 
	 * @return GBCEallShareIndex
	 */
	public Double calculateGBCEAllShareIndex();

}

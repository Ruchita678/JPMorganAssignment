package com.jpmorgan.simplestock.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.logging.Logger;

import com.jpmorgan.simplestock.dto.StockDao;
import com.jpmorgan.simplestock.dto.StockDaoImpl;
import com.jpmorgan.simplestock.entity.Stock;
import com.jpmorgan.simplestock.entity.StockTrade;
import com.jpmorgan.simplestock.entity.Trade;
import com.jpmorgan.simplestock.enums.StockType;
import com.jpmorgan.simplestock.enums.TradeType;
import com.jpmorgan.simplestock.exception.StockRuntimeException;
import com.jpmorgan.simplestock.util.Utils;

import static java.math.BigDecimal.valueOf;

public class StockServiceImpl implements StockService {

	private StockDao stockDao = new StockDaoImpl();;
	private Stock stock;
	private static Map<String, StockTrade> trade = new HashMap<String, StockTrade>();;
	private final static Logger LOGGER = Logger.getLogger(StockServiceImpl.class.getName());
	
	public StockServiceImpl() {
	}

	public StockServiceImpl(StockDao stockDao) {
		this.stockDao = stockDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jpmorgan.simplestock.service.StockService#setStock(java.lang.String)
	 */
	public void setStock(String stockSymbol) {
		this.stock = stockDao.getStockBySymbol(stockSymbol);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jpmorgan.simplestock.service.StockService#calculateDividendYield(java
	 * .math.BigDecimal)
	 */
	public BigDecimal calculateDividendYield(BigDecimal marketPrice) {
		BigDecimal dividendYield = BigDecimal.ZERO;
		try {
			isPositiveAndNonZero(marketPrice);
			if (stock.getType().equals(StockType.COMMON)) {
				dividendYield = stock.getLastDividend().divide(marketPrice, 2, RoundingMode.HALF_UP);
			} else {
				dividendYield = stock.getFixedDividend().multiply(stock.getParValue()).divide(marketPrice,2, RoundingMode.HALF_UP);
			}
		} catch (StockRuntimeException e) {
			LOGGER.info("calculateDividendYield:Failed to calculateDivident Yeild" + e);
		}
		return dividendYield;

	}

	public BigDecimal calculatePEratio(BigDecimal marketPrice) {
		isPositiveAndNonZero(marketPrice);
		BigDecimal pERatio = BigDecimal.ZERO;
		if (stock.getLastDividend().compareTo(BigDecimal.ZERO) > 0) {
			pERatio = marketPrice.divide(stock.getLastDividend(), 2, RoundingMode.HALF_UP);
		}
		return pERatio;
	}

	public void recordTrade() {
		StockTrade stockTrade = trade.get(stock.getSymbol());
		try {
			// Record some trades
			if (stockTrade == null) {
				stockTrade = new StockTrade();
			}
			// Logic to add trades for a given stock with
			// trade --> i quantity of shares at random price randomValue and
			// trade type ->BUY/SELL
			for (int i = 1; i <= 2; i++) {
				LOGGER.info("recordTrade:recording trade for :" + stock.getSymbol());
				Random r = new Random();
				Integer rangeMin = 1;
				Integer rangeMax = 10;
				double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
				stockTrade.recordTrade(new Trade(i, TradeType.BUY, valueOf(randomValue)));
				LOGGER.info(i + " no of shares bought at " + randomValue);
				Thread.sleep(500);
				randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
				stockTrade.recordTrade(new Trade(i, TradeType.SELL, valueOf(randomValue)));
				LOGGER.info(i + " no of shares sold at " + randomValue);
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			LOGGER.info("recordTrade:Failed to record trade for " + stock.getSymbol());
		}
		// updating trades of given stock in a trade map

		trade.put(stock.getSymbol(), stockTrade);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jpmorgan.simplestock.service.StockService#
	 * calculateVolumeWeightedStockPrice()
	 */
	public BigDecimal calculateVolumeWeightedStockPrice() {
		BigDecimal sumOfTradePriceTimesQuantity = BigDecimal.ZERO;
		final int TIME_DIFF = 15;
		final Integer TIME_DIFF_INMILLISECONDS = TIME_DIFF * 60 * 1000;
		Integer totalQuantity = 0;
		try {
			StockTrade stockTrades = trade.get(stock.getSymbol());
			Date now = new Date();
			Date startTime = new Date(now.getTime() - (TIME_DIFF_INMILLISECONDS));
			if (stockTrades != null) {
				SortedMap<Date, Trade> dateTrade = stockTrades.getTrades().tailMap(startTime);
				for (Trade trade : dateTrade.values()) {
					totalQuantity += trade.getQuanityOfShares();
					sumOfTradePriceTimesQuantity = sumOfTradePriceTimesQuantity.add(trade.getPrice())
							.multiply(valueOf(trade.getQuanityOfShares()));
				}
				if (totalQuantity == 0) {
					throw new StockRuntimeException("Quanity of shares purchased is zero");
				}
			} else {
				throw new StockRuntimeException(stock.getSymbol() + " has no trades");
			}

		} catch (StockRuntimeException e) {
			LOGGER.info("calculateVolumeWeightedStockPrice:");
		}

		return sumOfTradePriceTimesQuantity.divide(valueOf(totalQuantity), 2, RoundingMode.HALF_UP);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jpmorgan.simplestock.service.StockService#calculateGBCEAllShareIndex(
	 * )
	 */
	public Double calculateGBCEAllShareIndex() {
		BigDecimal prices = BigDecimal.ONE;
		Integer noOfStocksWithTraded = 0;
		for (StockTrade stockTrade : trade.values()) {
			BigDecimal stockPrice = stockTrade.getPrice();
			if (BigDecimal.ZERO.compareTo(stockPrice) < 0) {
				noOfStocksWithTraded++;
				prices = prices.multiply(stockTrade.getPrice());

			}
			if (prices.compareTo(BigDecimal.ONE) == 0) {
				return 0.0;
			}

		}
		return Utils.calculateNthRoot(noOfStocksWithTraded, prices);
	}

	private void isPositiveAndNonZero(BigDecimal num) {
		if (num.compareTo(BigDecimal.ZERO) <= 0) {
			throw new StockRuntimeException("Invalid value" + num);
		}

	}

}

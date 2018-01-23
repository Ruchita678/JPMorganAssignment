package com.jpmorgan.simplestock.entity;

import java.math.BigDecimal;
import static java.math.BigDecimal.valueOf;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

public class StockTrade {

	SortedMap<Date, Trade> trades;

	public StockTrade() {
		trades = new TreeMap<Date, Trade>();
	}

	public SortedMap<Date, Trade> getTrades() {
		return trades;
	}

	public void setTrades(SortedMap<Date, Trade> trades) {
		this.trades = trades;
	}

	public void recordTrade(Trade trade) {
		trades.put(new Date(), trade);
	}

	public BigDecimal getPrice() {
		BigDecimal price = BigDecimal.ZERO;
		for (Trade trade : trades.values()) {
			price = price.add(trade.getPrice().multiply(valueOf(trade.getQuanityOfShares())));
		}
		return price;
	}

}

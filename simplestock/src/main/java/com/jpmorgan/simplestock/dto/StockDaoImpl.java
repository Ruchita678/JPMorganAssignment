package com.jpmorgan.simplestock.dto;

import java.util.HashMap;
import java.util.Map;

import com.jpmorgan.simplestock.entity.Stock;
import com.jpmorgan.simplestock.enums.StockType;
import com.jpmorgan.simplestock.exception.StockRuntimeException;

import static java.math.BigDecimal.valueOf;

public class StockDaoImpl implements StockDao {

	Map<String, Stock> stockDb = new HashMap<String, Stock>();

	public StockDaoImpl() {
		// symbol,type,lastDividend,fixedDividend,parValue
		stockDb.put("TEA", new Stock("TEA", StockType.COMMON, valueOf(0), valueOf(100)));
		stockDb.put("POP", new Stock("POP", StockType.COMMON, valueOf(8), valueOf(100)));
		stockDb.put("ALE", new Stock("ALE", StockType.COMMON, valueOf(23), valueOf(60)));
		stockDb.put("GIN", new Stock("GIN", StockType.PREFERRED, valueOf(8), valueOf(2), valueOf(100)));
		stockDb.put("JOE", new Stock("JOE", StockType.COMMON, valueOf(13), valueOf(250)));

	}

	public Stock getStockBySymbol(String stockSymbol) {
		if (!stockDb.containsKey(stockSymbol)) {
			throw new StockRuntimeException("Please enter valid stock name");
		}
		return stockDb.get(stockSymbol);
	}

}

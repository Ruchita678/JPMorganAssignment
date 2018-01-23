package com.jpmorgan.simplestock.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.jpmorgan.simplestock.dto.StockDao;
import com.jpmorgan.simplestock.entity.Stock;
import com.jpmorgan.simplestock.enums.StockType;
import com.jpmorgan.simplestock.exception.StockRuntimeException;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceImplTest {

	private static final String STOCK_SYMBOL_ONE = "TEA";
	private static final String STOCK_SYMBOL_TWO = "POP";
	private final BigDecimal LAST_DIVIDEND = BigDecimal.ONE;
	private final BigDecimal FIXED_DIVIDEND = new BigDecimal(2);
	private final BigDecimal PAR_VALUE = new BigDecimal(100);
	private final BigDecimal VALID_MARKET_PRICE = new BigDecimal(100);
	private final BigDecimal INVALID_MARKET_PRICE = new BigDecimal(-100);

	@InjectMocks
	StockServiceImpl stockServiceImpl;
	@Mock
	StockDao stockDao;

	@Before
	public void setup() {
		stockServiceImpl = new StockServiceImpl(stockDao);
	}

	@Test
	public void calculateDividendYieldWithCommonStock() {

		Stock commonStock = new Stock(STOCK_SYMBOL_ONE, StockType.COMMON, LAST_DIVIDEND, PAR_VALUE);
		when(stockDao.getStockBySymbol(STOCK_SYMBOL_ONE)).thenReturn(commonStock);
		stockServiceImpl.setStock(STOCK_SYMBOL_ONE);

		assertEquals(stockServiceImpl.calculateDividendYield(VALID_MARKET_PRICE),
				commonStock.getLastDividend().divide(VALID_MARKET_PRICE, 2, RoundingMode.HALF_UP));

	}

	@Test
	public void calculateDividendYieldWithPreferredStock() {

		Stock preferredStock = new Stock(STOCK_SYMBOL_TWO, StockType.PREFERRED, LAST_DIVIDEND, FIXED_DIVIDEND,
				PAR_VALUE);
		when(stockDao.getStockBySymbol(STOCK_SYMBOL_TWO)).thenReturn(preferredStock);
		stockServiceImpl.setStock(STOCK_SYMBOL_TWO);

		assertEquals(stockServiceImpl.calculateDividendYield(VALID_MARKET_PRICE), preferredStock.getFixedDividend()
				.multiply(preferredStock.getParValue()).divide(VALID_MARKET_PRICE, 2, RoundingMode.HALF_UP));

	}

	@Test(expected = StockRuntimeException.class)
	public void calculatePERatioWithNegPrice() {
		Stock commonStock = new Stock(STOCK_SYMBOL_ONE, StockType.COMMON, LAST_DIVIDEND, PAR_VALUE);
		when(stockDao.getStockBySymbol(STOCK_SYMBOL_ONE)).thenReturn(commonStock);
		stockServiceImpl.setStock(STOCK_SYMBOL_ONE);
		stockServiceImpl.calculatePEratio(INVALID_MARKET_PRICE);
	}

	@Test
	public void calculatePERatioWithValidPrice() {
		Stock commonStock = new Stock(STOCK_SYMBOL_ONE, StockType.COMMON, LAST_DIVIDEND, PAR_VALUE);
		when(stockDao.getStockBySymbol(STOCK_SYMBOL_ONE)).thenReturn(commonStock);
		stockServiceImpl.setStock(STOCK_SYMBOL_ONE);
		assertEquals(stockServiceImpl.calculatePEratio(VALID_MARKET_PRICE),VALID_MARKET_PRICE.divide(commonStock.getLastDividend(), 2, RoundingMode.HALF_UP));

	}
}

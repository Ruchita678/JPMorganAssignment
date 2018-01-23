package com.jpmorgan.simplestock.entity;

import java.math.BigDecimal;

import com.jpmorgan.simplestock.enums.StockType;

public class Stock {

	private String symbol;
	private StockType type;
	private BigDecimal lastDividend;
	private BigDecimal fixedDividend;
	private BigDecimal parValue;

	/**
	 * Constructor for preferred stocks
	 * 
	 * @param symbol
	 * @param type
	 * @param lastDividend
	 * @param fixedDividend
	 * @param parValue
	 */
	public Stock(String symbol, StockType type, BigDecimal lastDividend, BigDecimal fixedDividend,
			BigDecimal parValue) {
		super();
		this.symbol = symbol;
		this.type = type;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
	}

	/**
	 * Constructor for Common stocks
	 * @param symbol
	 * @param type
	 * @param lastDividend
	 * @param parValue
	 */
	public Stock(String symbol, StockType type, BigDecimal lastDividend, BigDecimal parValue) {
		super();
		this.symbol = symbol;
		this.type = type;
		this.lastDividend = lastDividend;
		this.parValue = parValue;

	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public StockType getType() {
		return type;
	}

	public void setType(StockType type) {
		this.type = type;
	}

	public BigDecimal getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(BigDecimal lastDividend) {
		this.lastDividend = lastDividend;
	}

	public BigDecimal getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(BigDecimal fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	public BigDecimal getParValue() {
		return parValue;

	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	@Override
	public String toString() {
		return "Stock [symbol=" + symbol + ", type=" + type + ", lastDividend=" + lastDividend + ", fixedDividend="
				+ fixedDividend + ", parValue=" + parValue + "]";
	}

}

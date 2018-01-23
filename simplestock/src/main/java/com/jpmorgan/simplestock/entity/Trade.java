package com.jpmorgan.simplestock.entity;

import java.math.BigDecimal;

import com.jpmorgan.simplestock.enums.TradeType;

public class Trade {
	private Integer quanityOfShares;
	private TradeType type;
	private BigDecimal price;
	
	public Trade(Integer quanityOfShares, TradeType type,BigDecimal price) {	
		this.quanityOfShares = quanityOfShares;
		this.type = type;
		this.price = price;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getQuanityOfShares() {
		return quanityOfShares;
	}
	public void setQuanityOfShares(Integer quanityOfShares) {
		this.quanityOfShares = quanityOfShares;
	}
	public TradeType getType() {
		return type;
	}
	public void setType(TradeType type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Trade [quanityOfShares=" + quanityOfShares + ", type=" + type + ", price=" + price + "]";
	}
	
}

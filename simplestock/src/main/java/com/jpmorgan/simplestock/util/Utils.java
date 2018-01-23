package com.jpmorgan.simplestock.util;

import static java.lang.Math.pow;

import java.math.BigDecimal;

public class Utils {

	public static Double calculateNthRoot(Integer n, BigDecimal value) {
		if (n == 0) {
			return 0.0;
		}
		if (BigDecimal.ZERO.compareTo(value) == 0) {
			return 1.0;
		}
		return pow(value.doubleValue(), 1.0 / n);
	}

}

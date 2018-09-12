package com.wynprice.calculator;

import java.util.Map;

public interface CalculationType {
    double getValue(Map<String, Double> constantMap) throws MathExecuteException;
}

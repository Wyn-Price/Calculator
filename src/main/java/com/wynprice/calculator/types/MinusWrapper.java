package com.wynprice.calculator.types;

import com.wynprice.calculator.CalculationType;
import com.wynprice.calculator.InputReader;
import com.wynprice.calculator.MathExecuteException;

import java.util.Map;

public class MinusWrapper implements CalculationType {

    private final CalculationType type;

    public MinusWrapper(CalculationType type, InputReader reader) {
        this.type = type;
    }

    @Override
    public double getValue(Map<String, Double> constantMap) throws MathExecuteException {
        return -this.type.getValue(constantMap);
    }
}

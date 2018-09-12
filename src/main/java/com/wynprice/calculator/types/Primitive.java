package com.wynprice.calculator.types;

import com.wynprice.calculator.CalculationType;
import com.wynprice.calculator.InputReader;

import java.util.Map;

public class Primitive implements CalculationType {

    private final double value;

    protected Primitive(InputReader reader) {
        int startPos = reader.getPos();
        while (reader.hasMore()) {
            char c = reader.peakNextChar();
            if((c < '0' || c > '9') && c != '.') {
                break;
            }
            reader.getNextChar();
        }
        this.value = Double.parseDouble(reader.getFrom(startPos));
    }

    @Override
    public double getValue(Map<String, Double> constantMap) {
        return this.value;
    }
}

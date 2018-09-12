package com.wynprice.calculator.types;

import com.wynprice.calculator.CalculationType;
import com.wynprice.calculator.InputReader;
import com.wynprice.calculator.Main;

import java.util.Map;

public class Primitive implements CalculationType {

    private final double value;

    protected Primitive(InputReader reader) {
        Main.logger.startSection("Primitive", reader);

        int startPos = reader.getPos();
        while (reader.hasMore()) {
            char c = reader.peakNextChar();
            if((c < '0' || c > '9') && c != '.') {
                break;
            }
            reader.getNextChar();
        }
        this.value = Double.parseDouble(reader.getFrom(startPos));
        Main.logger.endSection(reader);
    }

    @Override
    public double getValue(Map<String, Double> constantMap) {
        return this.value;
    }
}

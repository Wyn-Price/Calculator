package com.wynprice.calculator.types;

import com.wynprice.calculator.*;

import java.util.Map;

public class Constant implements CalculationType {

    private final String constantName;

    public Constant(InputReader reader) throws MathParseException {
        Main.logger.startSection("Constant", reader);
        int startPos = reader.getPos();
        while (reader.hasMore()) {
            char c = reader.peakNextChar();

            //TODO: redo boolean logic to not just !nest the whole thing
            if(!(c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z')) {
                break;
            }
            reader.getNextChar();
        }
        this.constantName = reader.getFrom(startPos);
        Main.logger.endSection(reader);
    }

    @Override
    public double getValue(Map<String, Double> constantMap) throws MathExecuteException {
        if(!constantMap.containsKey(this.constantName)) {
            throw new MathExecuteException("Unknown Constant " + this.constantName);
        }
        return constantMap.get(this.constantName);
    }
}

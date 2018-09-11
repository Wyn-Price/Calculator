package com.wynprice.calculator.types;

import com.wynprice.calculator.CalculationType;
import com.wynprice.calculator.InputReader;
import com.wynprice.calculator.MathParseException;

public class Constant implements CalculationType {

    private final double constant;

    public Constant(InputReader reader) throws MathParseException {
        int startPos = reader.getPos();
        while (reader.hasMore()) {
            char c = reader.peakNextChar();
            if (c == ' ') {
                continue;
            }
            //TODO: redo boolean logic to not just !nest the whole thing
            if(!(c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z')) {
                break;
            }
            reader.getNextChar();
        }
        String consName = reader.getFrom(startPos);
        if(reader.hasConstant(consName)) {
            this.constant = reader.getConstant(consName);
        } else {
            throw new MathParseException(startPos, "Unknown Constant " + consName);
        }
    }

    @Override
    public double getValue() {
        return this.constant;
    }
}

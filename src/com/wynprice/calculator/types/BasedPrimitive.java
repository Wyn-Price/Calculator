package com.wynprice.calculator.types;

import com.wynprice.calculator.CalculationType;
import com.wynprice.calculator.InputReader;
import com.wynprice.calculator.MathExecuteException;
import com.wynprice.calculator.MathParseException;

import java.util.ArrayList;
import java.util.List;

public class BasedPrimitive implements CalculationType {

    private static final String CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final List<Character> CHARLIST = new ArrayList<>();

    static {
        for (char c : CHARSET.toCharArray()) {
            CHARLIST.add(c);
        }
    }

    private final String input;
    private final Expression radixExpression;


    public BasedPrimitive(InputReader reader) throws MathParseException {
        int startPos = reader.getPos();
        while (reader.hasMore()) {
            char c = reader.peakNextChar();
            if(!CHARLIST.contains(c)) {
                break;
            }
            reader.getNextChar();
        }
        this.input = reader.getFrom(startPos);
        if(!reader.isNextChar('_')) {
            throw new MathParseException(startPos, "Unacceptable base primitive input");
        }
        this.radixExpression = new Expression(reader);
    }

    @Override
    public double getValue() throws MathExecuteException {
        //Actual Math. Converted from -> https://github.com/Wyn-Price/Fireworks/blob/master/src/main/java/com/wynprice/fireworks/common/util/calculator/ExtraMathUtils.java

        double radix = this.radixExpression.getValue();

        if(radix == 0) {
            throw new MathExecuteException("Unable to process base conversion with radixExpression 0");
        }
        String noDecimalInput;
        int decimal = this.input.indexOf('.');
        if(decimal == -1) {
            decimal = this.input.length();
            noDecimalInput = this.input;
        } else {
            noDecimalInput = this.input.substring(0, decimal) + this.input.substring(decimal + 1, this.input.length());
        }
        double out = 0;
        for(int i = 0; i < noDecimalInput.length(); i++) {
            char c = noDecimalInput.charAt(i);
            int charIndex = CHARSET.indexOf(c);
            if(charIndex >= radix) {
                throw new MathExecuteException("Input character:" + c + " was larger than the radixExpression: " + radix);
            }
            if(charIndex == -1) {
                throw new MathExecuteException("Unexpected Character: " + c + ", at position: " + i);
            }
            out += charIndex * Math.pow(radix, decimal - i - 1);
        }
        return out;
    }
}

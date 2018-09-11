package com.wynprice.calculator;

public class MathParseException extends Exception {

    private final int startPos;

    public MathParseException(int startPos, String message) {
        super(message);
        this.startPos = startPos;
    }

    public int getStartPos() {
        return startPos;
    }
}

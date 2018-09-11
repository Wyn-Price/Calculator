package com.wynprice.calculator.exceptions;

public class MathException extends RuntimeException {

    public MathException() {
        super();
    }

    public MathException(String message) {
        super(message);
    }

    public MathException(String message, Throwable cause) {
        super(message, cause);
    }

    public MathException(Throwable cause) {
        super(cause);
    }

}

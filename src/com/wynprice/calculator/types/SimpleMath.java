package com.wynprice.calculator.types;

import com.wynprice.calculator.CalculationType;
import com.wynprice.calculator.MathExecuteException;

import java.util.function.BiFunction;

public class SimpleMath implements CalculationType {

    private final MathType mathType;
    private final CalculationType left;
    private final CalculationType right;

    public SimpleMath(MathType mathType, CalculationType left, CalculationType right) {
        this.mathType = mathType;
        this.left = left;
        this.right = right;
    }

    @Override
    public double getValue() throws MathExecuteException {
        return this.mathType.mathFunc.apply(this.left.getValue(), this.right.getValue());
    }

    public enum MathType {
        MOD(true, "%", (d1, d2) -> d1 % d2),
        TIMES(true, "*", (d1, d2) -> d1 * d2),
        DIVIDE(true, "/", (d1, d2) -> d1 / d2),
        PLUS(false, "+", (d1, d2) -> d1 + d2),
        MINUS(false, "-", (d1, d2) -> d1 - d2);

        private final boolean calculateBefore;
        private final String bit;
        private final BiFunction<Double, Double, Double> mathFunc;

        MathType(boolean calculateBefore, String bit, BiFunction<Double, Double, Double> mathFunc) {
            this.calculateBefore = calculateBefore;
            this.bit = bit;
            this.mathFunc = mathFunc;
        }

        public boolean shouldCalculateBefore() {
            return this.calculateBefore;
        }

        public static MathType getFromChar(char c) {
            for (MathType mathType : values()) {
                if(mathType.bit.equals(String.valueOf(c))) {
                    return mathType;
                }
            }
            return null;
        }
    }
}

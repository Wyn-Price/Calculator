package com.wynprice.calculator.types;

import com.wynprice.calculator.CalculationType;

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
    public double getValue() {
        return this.mathType.mathFunc.apply(this.left.getValue(), this.right.getValue());
    }

    public enum MathType {
        TIMES("*", (d1, d2) -> d1 * d2),
        DIVIDE("/", (d1, d2) -> d1 / d2),
        PLUS("+", (d1, d2) -> d1 + d2),
        MINUS("-", (d1, d2) -> d1 - d2);

        private final String bit;
        private final BiFunction<Double, Double, Double> mathFunc;

        MathType(String bit, BiFunction<Double, Double, Double> mathFunc) {
            this.bit = bit;
            this.mathFunc = mathFunc;
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

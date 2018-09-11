package com.wynprice.calculator;

import com.wynprice.calculator.types.Expression;

public class Main {
    public static void main(String[] args) {
        InputReader reader = new InputReader("2pow(2, 3)");
        try {
            Expression expression = new Expression(reader);
            try {
                System.out.println(expression.getValue());
            } catch (MathExecuteException e) {
                e.printStackTrace();
            }
        } catch (MathParseException e) {
            String out = reader.getFrom(e.getStartPos());
            System.out.println("UNABLE TO COMPILE CALCULATION");
            System.out.println("Reason: " + e.getMessage());
            System.out.println(out + "<-----HERE");
        }
    }
}

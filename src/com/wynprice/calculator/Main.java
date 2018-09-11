package com.wynprice.calculator;

import com.wynprice.calculator.types.Expression;

public class Main {
    public static void main(String[] args) {
        System.out.println(new Expression(new InputReader("3$x").withConstant("x", 2)).getValue());
    }
}

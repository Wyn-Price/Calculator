package com.wynprice.calculator;

import com.wynprice.calculator.types.Expression;

public class Main {
    public static void main(String[] args) {
        System.out.println(new Expression(new InputReader("_AAF_(sin(radians(90)) * pow(2, 4))")).getValue());
    }
}

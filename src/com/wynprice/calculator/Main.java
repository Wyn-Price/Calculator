package com.wynprice.calculator;

import com.wynprice.calculator.types.Expression;

public class Main {
    public static void main(String[] args) {
        System.out.println(new Expression(new StringReader("(5 + 3) * 3")).getValue());
    }
}

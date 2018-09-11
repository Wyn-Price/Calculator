package com.wynprice.calculator.types;

import com.wynprice.calculator.MathParseException;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface NamedFunctionType {
    int getParameterCount();

    String getName();

    double getValue(double[] adouble);

    //TODO: registry
    static NamedFunctionType getType(int startPos, String name) {
        for (SingleFunctions func : SingleFunctions.values()) {
            if(func.name.equals(name)) {
                return func;
            }
        }
        for (DoubleFunctions func : DoubleFunctions.values()) {
            if(func.name.equals(name)) {
                return func;
            }
        }
        throw new MathParseException(startPos, "Unknown function " + name);
    }

    enum SingleFunctions implements NamedFunctionType {
        RADIANS("toRadians", Math::toRadians),
        SIN("sin", Math::sin);

        private final String name;
        private final Function<Double, Double> func;

        SingleFunctions(String name, Function<Double, Double> func) {
            this.name = name;
            this.func = func;
        }

        @Override
        public int getParameterCount() {
            return 1;
        }

        @Override
        public double getValue(double[] adouble) {
            return this.func.apply(adouble[0]);
        }

        @Override
        public String getName() {
            return this.name;
        }
    }

    enum DoubleFunctions implements NamedFunctionType {
        ;

        private final String name;
        private final BiFunction<Double, Double, Double> func;

        DoubleFunctions(String name, BiFunction<Double, Double, Double> func) {
            this.name = name;
            this.func = func;
        }

        @Override
        public int getParameterCount() {
            return 2;
        }

        @Override
        public double getValue(double[] adouble) {
            return this.func.apply(adouble[0], adouble[1]);
        }

        @Override
        public String getName() {
            return this.name;
        }
    }
}

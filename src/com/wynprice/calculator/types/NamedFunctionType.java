package com.wynprice.calculator.types;

import com.wynprice.calculator.MathParseException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface NamedFunctionType {
    int getParameterCount();

    double getValue(double[] adouble);


    Map<String, NamedFunctionType> functionMap = new HashMap<String, NamedFunctionType>(){{
        for (SingleFunction func : SingleFunction.values()) {
            this.put(func.name().toLowerCase(), func);
        }
        for (DoubleFunction func : DoubleFunction.values()) {
            this.put(func.name().toLowerCase(), func);
        }
    }};

    static NamedFunctionType getType(int startPos, String name) throws MathParseException {
        if(functionMap.containsKey(name)) {
            return functionMap.get(name);
        }
        throw new MathParseException(startPos, "Unknown function " + name);
    }

    @SuppressWarnings("unused")
    enum SingleFunction implements NamedFunctionType {
        SQRT(Math::sqrt),
        CBRT(Math::cbrt),
        FLOOR(Math::floor),
        CEIL(Math::ceil),
        ROUND(in -> (double)Math.round( in)),
        ABS(Math::abs),
        LOG(Math::log),
        LOG10(Math::log10),
        LOG1P(Math::log1p),
        EXP(Math::exp),
        EXPM1(Math::expm1),
        EXPONANT(in ->(double)Math.getExponent(in)),
        DEGREES(Math::toDegrees),
        RADIANS(Math::toRadians),
        SIN(Math::sin),
        ASIN(Math::asin),
        SINH(Math::sinh),
        COS(Math::cos),
        ACOS(Math::acos),
        COSH(Math::cosh),
        TAN(Math::atan),
        ATAN(Math::atan),
        TANH(Math::tanh);

        private final Function<Double, Double> func;

        SingleFunction(Function<Double, Double> func) {
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
    }

    @SuppressWarnings("unused")
    enum DoubleFunction implements NamedFunctionType {
        ROOT((num, root) -> root == 0 ? 1 : (num < 0 ? -1 : 1) * Math.pow(Math.abs(num), 1 / root)),
        MAX(Math::max),
        MIN(Math::min),
        HYPOT(Math::hypot),
        POW(Math::pow),
        IEEEREMAINDER(Math::IEEEremainder);

        private final BiFunction<Double, Double, Double> func;

        DoubleFunction(BiFunction<Double, Double, Double> func) {
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
    }
}

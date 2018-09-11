package com.wynprice.calculator.types;

import com.wynprice.calculator.MathParseException;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface NamedFunctionType {
    int getParameterCount();

    String getName();

    double getValue(double[] adouble);

    //TODO: registry
    static NamedFunctionType getType(int startPos, String name) throws MathParseException {
        for (SingleFunctions func : SingleFunctions.values()) {
            if(func.name().equalsIgnoreCase(name)) {
                return func;
            }
        }
        for (DoubleFunctions func : DoubleFunctions.values()) {
            if(func.name().equalsIgnoreCase(name)) {
                return func;
            }
        }
        throw new MathParseException(startPos, "Unknown function " + name);
    }

    @SuppressWarnings("unused")
    enum SingleFunctions implements NamedFunctionType {
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

        SingleFunctions(Function<Double, Double> func) {
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
            return this.name();
        }
    }

    @SuppressWarnings("unused")
    enum DoubleFunctions implements NamedFunctionType {
        ROOT((num, root) -> root == 0 ? 1 : (num < 0 ? -1 : 1) * Math.pow(Math.abs(num), 1 / root)),
        MAX(Math::max),
        MIN(Math::min),
        HYPOT(Math::hypot),
        POW(Math::pow),
        IEEEREMAINDER(Math::IEEEremainder);

        private final BiFunction<Double, Double, Double> func;

        DoubleFunctions(BiFunction<Double, Double, Double> func) {
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
            return this.name();
        }
    }
}

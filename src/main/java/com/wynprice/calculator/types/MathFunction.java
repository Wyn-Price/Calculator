package com.wynprice.calculator.types;

import com.wynprice.calculator.CalculationType;
import com.wynprice.calculator.InputReader;
import com.wynprice.calculator.MathExecuteException;
import com.wynprice.calculator.MathParseException;

public class MathFunction implements CalculationType {

    private final NamedFunctionType function;
    private final CalculationType[] types;

    public MathFunction(InputReader reader) throws MathParseException {
        int startPos = reader.getPos();
        boolean ended = false;
        while (reader.hasMore()) {
            char c = reader.peakNextChar();
            if (c == '(') {
                ended = true;
                break;
            }
            reader.getNextChar();
        }
        String funcEnd = reader.getFrom(startPos);
        this.function = NamedFunctionType.getType(startPos, funcEnd);
        if (!ended) {
            throw new MathParseException(startPos, "Un-opened brackets on function \"" + funcEnd + "\"");
        }
        reader.getNextChar(); //Skip the (
        int count = this.function.getParameterCount();
        this.types = new CalculationType[count];

        for (int i = 0; i < count; i++) {
            this.types[i] = new Expression(reader, true);
            if (i + 1 != count == !reader.isNextChar(',')) {
                throw new MathParseException(startPos, i + 1 != count ? "Expected " + count + " inputs, found " + i : "Unexpected comma" );
            }
        }
    }

    @Override
    public double getValue() throws MathExecuteException {
        double[] adouble = new double[this.types.length];
        for (int i = 0; i < this.types.length; i++) {
            adouble[i] = this.types[i].getValue();
        }
        return this.function.getValue(adouble);
    }
}

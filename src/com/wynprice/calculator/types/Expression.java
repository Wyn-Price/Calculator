package com.wynprice.calculator.types;

import com.wynprice.calculator.CalculationType;
import com.wynprice.calculator.StringReader;
import com.wynprice.calculator.exceptions.MathParseException;

public class Expression implements CalculationType {

    private final CalculationType calculation;

    public Expression(StringReader reader) {
        CalculationType left = null;
        CalculationType right = null;

        SimpleMath.MathType mathType = null;

        int startPos = reader.getPos();

        boolean doneLeft = false;
        while(reader.hasMore()) {
            char c = reader.getNextChar();
            CalculationType exp = null;

            if(reader.getCharacter() == '(') {
                exp = new Expression(reader);
            } else if(reader.getCharacter() == ')') {
                break;
            } else if((c >= '0' && c <= '9') || c == '.') {
                exp = new Primitive(reader);
            } else {
                SimpleMath.MathType type = SimpleMath.MathType.getFromChar(c);
                if(type != null) {
                    mathType = type;
                }
            }

            if(exp != null) {
                if(doneLeft) {
                    if(mathType == null) {
                        throw new MathParseException("Tried to run an expression with no maths");
                    }
                    right = exp;
                } else {
                    left = exp;
                    doneLeft = true;
                }
            }
        }
        if(right == null && mathType == null) {
            this.calculation = left;
        } else if(left != null){
            this.calculation = new SimpleMath(mathType, left, right);
        } else {
            throw new MathParseException("Invalid Input in expression " + reader.getFrom(startPos));
        }
    }

    @Override
    public double getValue() {
        return this.calculation.getValue();
    }
}

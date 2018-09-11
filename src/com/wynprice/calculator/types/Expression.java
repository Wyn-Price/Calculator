package com.wynprice.calculator.types;

import com.wynprice.calculator.CalculationType;
import com.wynprice.calculator.InputReader;
import com.wynprice.calculator.MathExecuteException;
import com.wynprice.calculator.MathParseException;

public class Expression implements CalculationType {

    private final CalculationType calculation;

    public Expression(InputReader reader) throws MathParseException {
        this(reader, false);
    }

    public Expression(InputReader reader, boolean isFunc) throws MathParseException {
        CalculationType left = null;
        CalculationType right = null;

        SimpleMath.MathType mathType = null;

        int startPos = reader.getPos();

        boolean doneLeft = false;
        while(reader.hasMore()) {
            char c = reader.getNextChar();
            CalculationType exp = null;

            if(reader.getCharacter() == '(') { //Create a new expression
                exp = new Expression(reader);
            } else if(reader.getCharacter() == ')') { //End the expression
                break;
            } else if(isFunc && reader.getCharacter() == ',') { //If we're in a function and found the end of parameter
                break;
            } else if(reader.getCharacter() == '_') {//Parse the input from an different base
                reader.getNextChar();//Get the characters after the '"', so move the pointer here
                exp = new BasedPrimitive(reader);
            } else if(reader.getCharacter() == '$') { //Use the constants
                reader.getNextChar();//Get the characters after the '$', so move the pointer here
                exp = new Constant(reader);
            } else if((c >= '0' && c <= '9') || c == '.') { //Parse the number as a primitive
                exp = new Primitive(reader);
            } else if(c >= 'A' && c <= 'Z' || c >= 'a' && c <= 'z') { //Try and run the expression as a function
                exp = new MathFunction(reader);
            } else { //Else type and parse it as a math symbol.
                SimpleMath.MathType type = SimpleMath.MathType.getFromChar(c);
                if(type != null) {
                    mathType = type;
                } else {
                    throw new MathParseException(startPos, "I don't know how to process this...");
                }
            }

            if(exp != null) {
                if(doneLeft) {
                    if(mathType == null) {
                        mathType = SimpleMath.MathType.TIMES; //Set math type to times. This allows for stuff like 5(2 + 3).
                    }
                    right = exp;
                } else {
                    left = exp;
                    doneLeft = true;
                }
            }
        }
        if(right == null && mathType == null) {
            if(left == null) {
                throw new MathParseException(startPos, "Empty Expression");
            }
            this.calculation = left;
        } else if(left != null){
            this.calculation = new SimpleMath(mathType, left, right);
        } else {
            throw new MathParseException(startPos, "Invalid Input in expression " + reader.getFrom(startPos));
        }
    }

    @Override
    public double getValue() throws MathExecuteException {
        return this.calculation.getValue();
    }
}

package com.wynprice.calculator.types;

import com.wynprice.calculator.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Expression implements CalculationType {

    private final CalculationType calculation;

    public Expression(InputReader reader) throws MathParseException {
        this(reader, false);
    }

    public Expression(InputReader reader, boolean isFunc) throws MathParseException {
        Main.logger.startSection("Expression", reader);

        List<CalculationType> types = new ArrayList<>();
        List<SimpleMath.MathType> corrospondingTypes = new ArrayList<>();
        int startPos = reader.getPos();

        boolean prevHadExp = false;

        while(reader.hasMore()) {
            char c = reader.getNextChar();
            CalculationType exp = null;
            SimpleMath.MathType mathType = null;

            if(reader.getCharacter() == ' ') {
                continue;
            } else if(reader.getCharacter() == '(') { //Create a new expression
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

            if(prevHadExp) {
                corrospondingTypes.add(mathType == null ? SimpleMath.MathType.TIMES : mathType);
            }

            if(exp != null) {
                types.add(exp);
            }

            prevHadExp = exp != null;
        }

        List<CalculationType> simpleTypes = new ArrayList<>();
        List<SimpleMath.MathType> simpleCorrospondingTypes = new ArrayList<>();

        simpleTypes.add(types.get(0));

        for (int i = 0; i < corrospondingTypes.size(); i++) {
            SimpleMath.MathType type = corrospondingTypes.get(i);
            if(type.shouldCalculateBefore()) {
                simpleTypes.add(new SimpleMath(type, simpleTypes.get(simpleTypes.size() - 1), types.get(i + 1)));
                simpleTypes.remove(simpleTypes.size() - 2);
            } else {
                simpleTypes.add(types.get(i + 1));
                simpleCorrospondingTypes.add(type);
            }
        }

        CalculationType previous = null;
        for (int i = 0; i < simpleTypes.size(); i++) {
            CalculationType calculation = simpleTypes.get(i);
            if(i != 0) {
                previous = new SimpleMath(simpleCorrospondingTypes.get(i - 1), previous, calculation);
            } else {
                previous = calculation;
            }
        }

        this.calculation = previous;
        Main.logger.endSection(reader);
    }

    @Override
    public double getValue(Map<String, Double> constantMap) throws MathExecuteException {
        return this.calculation.getValue(constantMap);
    }
}

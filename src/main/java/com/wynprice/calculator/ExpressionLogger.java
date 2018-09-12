package com.wynprice.calculator;

import java.util.ArrayList;
import java.util.List;

public class ExpressionLogger {
    public static final String SEPERATOR = "-----";

    private int level;

    private List<String> typeStack = new ArrayList<>();
    private List<Integer> startPosList = new ArrayList<>();

    public void startSection(String section, InputReader reader) {
        this.typeStack.add(section);
        this.startPosList.add(reader.getPos());
        for (int i = 0; i < this.level; i++) {
            System.out.print(SEPERATOR );
        }
        System.out.print("|+      ");
        System.out.println("Started Calculating '" + section + "' with position '" + reader.getPos() + "'");
        this.level++;
    }

    public void endSection(InputReader reader) {
        this.level--;
        String removed = this.typeStack.remove(this.typeStack.size() - 1);
        int posRemoved = this.startPosList.remove(this.startPosList.size() - 1);
        for (int i = 0; i < this.level; i++) {
            System.out.print(SEPERATOR);
        }
        System.out.print("|-      ");
        System.out.println("Calculated '" + removed + "'. Used characters: '" + reader.getFrom(posRemoved) + "'");
    }
}

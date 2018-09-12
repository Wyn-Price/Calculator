package com.wynprice.calculator;

public class InputReader {

    private final String input;

    private int pos = -1;
    private char character;

    public InputReader(String input) {
        this.input = input;
    }

    public char getNextChar() {
        if(++this.pos < this.input.length()) {
            return this.character = this.input.charAt(this.pos);
        } else {
            return this.character = (char)-1;
        }
    }

    public char peakNextChar() {
        if(this.pos + 1 < this.input.length()) {
            return this.character = this.input.charAt(this.pos + 1);
        } else {
            return this.character = (char)-1;
        }
    }

    public boolean isNextChar(char c) {
        while (this.character == ' ') {
            getNextChar();
        }
        if (this.character == c) {
            getNextChar();
            return true;
        }
        return false;
    }

    public boolean hasMore() {
        return this.pos + 1 < this.input.length();
    }

    public int getPos() {
        return this.pos == -1 ? 0 : this.pos;
    }

    public String getFrom(int fromPos) {
        return this.input.substring(fromPos, Math.min(this.input.length(), this.pos + 1));
    }

    public char getCharacter() {
        return character;
    }
}

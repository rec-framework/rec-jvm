package net.kimleo.rec.sepval.parser;

public class ParseState {
    private final String input;
    int index = 0;

    public ParseState(String input) {
        this.input = input;
    }

    public int getSize() {
        return input.length();
    }

    public Character current() {
        return eof() ? null : input.charAt(index);
    }

    public boolean eof() {
        return index >= input.length();
    }

    public Character next() {
        index ++;
        return current();
    }
}

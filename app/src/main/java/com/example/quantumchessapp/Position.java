package com.example.quantumchessapp;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object p) {
        if (this == p) {
            return true;
        }
        if (!(p instanceof Position)) {
            return false;
        }
        Position Position = (Position) p;
        return x == Position.x && y == Position.y;
    }

    @Override
    public int hashCode() {
        int ergebnis = x;
        ergebnis = 31 * ergebnis + y;
        return ergebnis;
    }
}

package com.example.quantumchessapp.figuren;

import com.example.quantumchessapp.spiel.Position;

public class Springer extends Spielfigur {

    public Springer() {
    }

    @Override
    public void nimmtPlatz(Position position) {
        System.out.print("S");
    }
}

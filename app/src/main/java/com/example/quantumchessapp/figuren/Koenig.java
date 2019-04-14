package com.example.quantumchessapp.figuren;

import com.example.quantumchessapp.spiel.Position;

public class Koenig extends Spielfigur {

    public Koenig() {
    }

    @Override
    public void nimmtPlatz(Position position) {
        System.out.print("K");
    }
}

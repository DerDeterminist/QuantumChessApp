package com.example.quantumchessapp.figuren;

import com.example.quantumchessapp.spiel.Position;

public class Laeufer extends Spielfigur {

    public Laeufer() {
    }

    @Override
    public void nimmtPlatz(Position position) {
        System.out.print("L");
    }
}

package com.example.quantumchessapp.figuren;

import com.example.quantumchessapp.spiel.Position;

public class Turm extends Spielfigur {

    public Turm() {
    }

    @Override
    public void nimmtPlatz(Position position) {
        System.out.print("T");
    }
}

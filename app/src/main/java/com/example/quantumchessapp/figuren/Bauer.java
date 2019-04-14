package com.example.quantumchessapp.figuren;

import com.example.quantumchessapp.spiel.Position;

public class Bauer extends Spielfigur {

    public Bauer() {
    }

    @Override
    public void nimmtPlatz(Position position) {
        System.out.print("B");
    }
}

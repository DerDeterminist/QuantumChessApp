package com.example.quantumchessapp.figuren;

import com.example.quantumchessapp.spiel.Position;

public abstract class Spielfigur {
    // jede Spielfigur bekommt seinen eigenen Platz auf dem Brett
    public abstract void nimmtPlatz(Position position);
}

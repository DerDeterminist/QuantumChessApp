package com.example.quantumchessapp.applikation;

import com.example.quantumchessapp.figuren.Spielfigur;
import com.example.quantumchessapp.spiel.Position;
import com.example.quantumchessapp.spiel.Schachbrett;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        HashMap<Position, Spielfigur> mapBelegung = new HashMap<>();
        Schachbrett brett = new Schachbrett(8, 8);
        brett.init(mapBelegung);
        new View(mapBelegung, brett);

    }
}

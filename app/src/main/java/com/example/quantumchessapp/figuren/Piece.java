package com.example.quantumchessapp.figuren;

import com.example.quantumchessapp.spiel.Player;
import com.example.quantumchessapp.spiel.Position;

public abstract class Piece
{
   private Position position;
   private Player owner;
   private double status;

   public Piece(Position position, Player owner, double status)
   {
      this.position = position;
      this.owner = owner;
      this.status = status;
   }
}
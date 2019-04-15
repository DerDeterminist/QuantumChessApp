package com.example.quantumchessapp.figuren;

import com.example.quantumchessapp.spiel.Player;
import com.example.quantumchessapp.spiel.Position;

public abstract class Piece
{
   public final double MAX_STATUS;

   private Position position;
   private Player owner;
   private double status;

   public Piece(Position position, double maxStatus)
   {
      this.position = position;
      this.MAX_STATUS = maxStatus;
   }
}
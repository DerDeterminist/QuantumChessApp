package com.example.quantumchessapp.spiel;

import com.example.quantumchessapp.figuren.Piece;

import java.util.List;

public class Player
{

   private List<Piece> pieces;

   private int id;

   public int getId()
   {
      return id;
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o)
      {
         return true;
      }
      if (o == null || getClass() != o.getClass())
      {
         return false;
      }
      return id == ((Player) o).id;
   }

   @Override
   public int hashCode()
   {
      return id;
   }
}
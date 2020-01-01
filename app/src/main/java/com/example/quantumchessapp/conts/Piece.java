package com.example.quantumchessapp.conts;


import lombok.Data;

@Data
public class Piece
{
   private String id;
   private PieceType type;
   private PieceColor color;
   private double status;
   private int x;
   private int y;

   @Override
   public int hashCode()
   {
      return id.hashCode();
   }

   @Override
   public boolean equals(Object o)
   {
      if (o == null)
      {
         return false;
      }
      if (this == o)
      {
         return true;
      }
      if (!(o instanceof Piece))
      {
         return false;
      }
      return ((Piece) o).getId().equals(id);
   }
}

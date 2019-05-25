package com.example.api.Containter;

public enum PieceType
{
   BISHOP("Bishop"),
   KING("King"),
   KNIGHT("Knight"),
   PAWN("Pawn"),
   QUEEN("Queen"),
   ROOK("Rook");

   private String name;

   PieceType(String name)
   {
      this.name = name;
   }

   public String getName()
   {
      return name;
   }
}

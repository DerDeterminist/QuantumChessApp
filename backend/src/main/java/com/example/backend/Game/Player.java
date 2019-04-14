package com.example.backend.Game;

import com.example.backend.Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Player
{
   private List<Piece> pieces;

   void setPieces(List<Piece> pieces)
   {
      this.pieces = new ArrayList<>(pieces);
   }

   List<Piece> getPieces()
   {
      return pieces;
   }

   @SuppressWarnings("UnusedReturnValue")
   public boolean removePiece(Piece piece)
   {
      return pieces.remove(piece);
   }

   public void addPiece(Piece piece)
   {
      pieces.add(piece);
   }
}
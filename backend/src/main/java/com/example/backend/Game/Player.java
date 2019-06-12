package com.example.backend.Game;

import com.example.backend.Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Player
{
   private int id;
   private List<Piece> pieces;

   public Player(int id)
   {
      this.id = id;
   }

   void setPieces(List<Piece> pieces)
   {
      this.pieces = new ArrayList<>(pieces);
   }

   List<Piece> getPieces()
   {
      return pieces;
   }

   public void removePiece(Piece piece)
   {
      pieces.remove(piece);
   }

   public void addPiece(Piece piece)
   {
      pieces.add(piece);
   }

   public int getId()
   {
      return id;
   }
}
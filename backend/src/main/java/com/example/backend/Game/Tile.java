package com.example.backend.Game;

import com.example.backend.Game.Board.Direction;
import com.example.backend.Game.Board.Itr;
import com.example.backend.Pieces.Piece;

import java.util.Iterator;
import java.util.Optional;


public class Tile
{

   private int x;
   private int y;
   private Board board;
   private Piece piece;

   public Tile(int x, int y, Board board)
   {
      this.x = x;
      this.y = y;
      this.board = board;
   }

   public Optional<Piece> getPiece()
   {
      return piece == null ? Optional.empty() : Optional.of(piece);
   }

   public void setPiece(Piece piece)
   {
      if (this.piece != null || piece == null)
      {
         board.getChange().addRemoved(this.piece);
      }
      else
      {
         board.getChange().addAdded(piece);
      }
      this.piece = piece;
   }

   public int getX()
   {
      return x;
   }

   public int getY()
   {
      return y;
   }

   public Tile getCloseOne(Direction... direction)
   {
      int xSum = 0;
      int ySum = 0;
      for (Direction direction1 : direction)
      {
         xSum += direction1.x;
         ySum += direction1.y;
      }
      return board.getTileAt(x + xSum, y + ySum);
   }

   public Iterator getIterator(Direction direction)
   {
      return new Itr(this, direction);
   }

   public void reportStatusChange()
   {
      board.getChange().addChanged(piece);
   }
}
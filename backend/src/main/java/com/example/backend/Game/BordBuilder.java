package com.example.backend.Game;

import com.example.backend.Pieces.Piece;

import java.util.ArrayList;
import java.util.List;


class BordBuilder
{

   static void buildRectangle(Board board, Player... players) throws OutOfBoardException
   {
      for (Player player : players)
      {
         if (player.getPieces().stream().anyMatch(piece -> {
            Tile currentTile = piece.getCurrentTile();
            return currentTile.getX() > board.getWith() || currentTile.getY() > board.getHeight() || currentTile
                  .getX() < 0 || currentTile.getY() < 0;
         }))
         {
            throw new OutOfBoardException("a piece is out of the bord");
         }
      }
      List<Tile> ret = new ArrayList<>();
      for (int i = 0; i < board.getWith(); i++)
      {
         for (int j = 0; j < board.getHeight(); j++)
         {
            boolean present = false;

            for (Player player : players)
            {
               for (Piece piece : player.getPieces())
               {
                  Tile currentTile = piece.getCurrentTile();
                  if (currentTile.getX() == i && currentTile.getY() == j)
                  {
                     ret.add(currentTile);
                     present = true;
                  }
               }
            }
            if (!present)
            {
               ret.add(new Tile(i, j, board));
            }
         }
      }
      board.setTiles(ret);
   }
}

package com.example.backend.Pieces;

import com.example.backend.Game.Board;
import com.example.backend.Game.Player;
import com.example.backend.Game.Tile;

import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Bishop extends InLine
{

   public Bishop(Tile tile, Player owner)
   {
      super(tile, owner, Board.Direction.getDiagonals());
   }

   @Override
   Set<Tile> oneMove(Tile tile, boolean qMove)
   {
      return validTilesForOneMoveInARow(tile, qMove);
   }

   @Override
   Set<Tile> nMove(Tile toGo)
   {
      return oneMove(toGo, false);
   }

   @Override
   Set<Tile> qMove(Tile toGo)
   {
      Set<Tile> tiles = oneMove(toGo, true);
      tiles.addAll(tiles.stream().flatMap(tile -> oneMove(tile, true).stream())
            .collect(Collectors.toList()));
      return tiles.stream().filter(tile -> !tile.getPiece().isPresent()).collect(Collectors.toSet());
   }
}
package com.example.backend.Pieces;

import com.example.backend.Game.Board;
import com.example.backend.Game.Player;
import com.example.backend.Game.Tile;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class King extends Piece
{

   public King(Tile tile, Player owner)
   {
      super(tile, owner);
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
      tiles.addAll(tiles.stream().flatMap(tile -> oneMove(tile, true).stream()).collect(Collectors.toList()));
      return tiles.stream().filter(tile1 -> !tile1.getPiece().isPresent()).collect(Collectors.toSet());
   }

   @Override
   Set<Tile> oneMove(Tile tile, boolean qMove)
   {
      return Arrays.stream(Board.Direction.values()).map(tile::getCloseOne)
            .filter(tile1 -> qMove ? getQPredicate().test(tile1) : getNPredicate().test(tile1)).collect(Collectors.toSet());
   }
}

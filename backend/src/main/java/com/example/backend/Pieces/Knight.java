package com.example.backend.Pieces;

import com.example.backend.Game.Board.Direction;
import com.example.backend.Game.Player;
import com.example.backend.Game.Tile;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Knight extends Piece
{
   public Knight(Tile tile, Player owner)
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
      return Stream.of((
                  tile.getCloseOne(Direction.LEFT, Direction.LEFT, Direction.TOP)),
            tile.getCloseOne(Direction.LEFT, Direction.LEFT, Direction.DOWN),
            tile.getCloseOne(Direction.RIGHT, Direction.RIGHT, Direction.TOP),
            tile.getCloseOne(Direction.DOWN, Direction.DOWN, Direction.RIGHT),
            tile.getCloseOne(Direction.DOWN, Direction.DOWN, Direction.LEFT),
            tile.getCloseOne(Direction.TOP, Direction.TOP, Direction.RIGHT),
            tile.getCloseOne(Direction.TOP, Direction.TOP, Direction.LEFT),
            tile.getCloseOne(Direction.RIGHT, Direction.RIGHT, Direction.DOWN))
            .filter(tile1 -> qMove ? getQPredicate().test(tile1) : getNPredicate().test(tile1))
            .collect(Collectors.toSet());
   }
}

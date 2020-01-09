package com.example.backend.Pieces;

import com.example.backend.Game.Board.Direction;
import com.example.backend.Game.Player;
import com.example.backend.Game.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Pawn extends Piece
{

   private final int initialY;
   private final Direction topOrDown;
   private final Predicate<Tile> nHit;
   private final Predicate<Tile> nMove;
   private final Predicate<Tile> qMove;

   public Pawn(Tile tile, Player owner)
   {
      super(tile, owner);
      this.initialY = tile.getY();
      this.topOrDown = initialY == 1 ? Direction.TOP : Direction.DOWN;
      nHit = tile1 -> tile1 != null && tile1.getPiece().isPresent() &&
            !tile1.getPiece().get().getOwner().equals(Pawn.this.getOwner());
      nMove = tile13 -> tile13 != null &&
            (!tile13.getPiece().isPresent() || !tile13.getPiece().isPresent() && tile13.getPiece().get().getStatus() < MAX_STATUS);
      qMove = tile14 ->
            !tile14.getPiece().isPresent() || tile14.getPiece().isPresent() && tile14.getPiece().get().getStatus() < MAX_STATUS;
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
      tiles.addAll(tiles.stream().flatMap(tile -> oneMove(tile, true).stream()).filter(tile -> !tiles.contains(tile))
            .collect(Collectors.toList()));
      return tiles.stream().filter(tile1 -> !tile1.getPiece().isPresent()).collect(Collectors.toSet());
   }

   @Override
   Set<Tile> oneMove(Tile tile, boolean qMove)
   {
      List<Tile> hit = new ArrayList<>();
      List<Tile> move = new ArrayList<>();

      move.add(tile.getCloseOne(topOrDown));
      hit.add(tile.getCloseOne(topOrDown, Direction.LEFT));
      hit.add(tile.getCloseOne(topOrDown, Direction.RIGHT));
      if (tile.getY() == initialY)
      {
         Tile closeOne = tile.getCloseOne(topOrDown);
         if (!closeOne.getPiece().isPresent())
         {
            move.add(closeOne.getCloseOne(topOrDown));
         }
      }
      Set<Tile> ret =
            hit.stream().filter(tile1 -> !qMove && nHit.test(tile1)).collect(Collectors.toSet());
      ret.addAll(move.stream().filter(tile1 -> qMove ? this.qMove.test(tile1) : nMove.test(tile1))
            .collect(Collectors.toSet()));
      return ret;
   }
}

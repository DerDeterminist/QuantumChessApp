package com.example.backend.Pieces;

import com.example.backend.Game.Board;
import com.example.backend.Game.Player;
import com.example.backend.Game.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

abstract class InLine extends Piece
{

   private List<Board.Direction> directions;

   /**
    * Extend this if you want a Piece that can move in a Line like
    * the Rook or the Bishop. For movement from Point to Point see {@code Piece}
    *
    * @param tile       tile the piece starts on
    * @param owner      the owner
    * @param directions directions the Piece will be able to move in
    * @see Piece
    */
   InLine(Tile tile, Player owner, List<Board.Direction> directions)
   {
      super(tile, owner);
      this.directions = directions;
   }

   @Override
   List<Tile> getEntangledTiles(Tile newTile)
   {
      List<List<Tile>> possiblePaths = new ArrayList<>();
      List<Tile> firstMove = new ArrayList<>(oneMove(tile, true)); // fine
      for (Tile secondTile : firstMove)
      {
         List<Tile> secondMove = new ArrayList<>(findPath(secondTile, newTile));
         if (!secondMove.isEmpty())
         {
            secondMove.addAll(findPath(tile, newTile));
            possiblePaths.add(secondMove);
         }
      }
      if (possiblePaths.size() == 1)
      {
         return possiblePaths.get(0);
      }

      double minStatus = Double.MAX_VALUE;
      List<Tile> minPath = null;
      for (List<Tile> possiblePath : possiblePaths)
      {
         double sum = possiblePath.stream().mapToDouble(tile -> {
            double ret = 0;
            if (tile.getPiece().isPresent())
            {
               ret = tile.getPiece().get().getStatus();
            }
            return ret;
         }).sum();
         if (sum < minStatus)
         {
            minStatus = sum;
            minPath = possiblePath;
         }
      }
      return minPath;
   }

   Set<Tile> validTilesForOneMoveInARow(Tile tile, boolean qMove)
   {
      return directions.stream().map(tile::getIterator).flatMap(iterator -> {
         List<Tile> tiles = new ArrayList<>();
         while (iterator.hasNext())
         {
            Tile next = (Tile) iterator.next();
            if (qMove ? getQPredicate().test(next) : getNPredicate().test(next))
            {
               tiles.add(next);
            }
            else
            {
               break;
            }
         }
         return tiles.stream();
      }).collect(Collectors.toSet());
   }

   private List<Tile> findPath(Tile start, Tile end) // todo über alle möglichen wege nicht nur der beste
   {
      return directions.stream().map(start::getIterator).map(iterator -> {
         List<Tile> tiles = new ArrayList<>();
         while (iterator.hasNext())
         {
            Tile next = (Tile) iterator.next();
            if (getQPredicate().test(next))
            {
               tiles.add(next);
               if (next.equals(end))
               {
                  break;
               }
            }
            else
            {
               break;
            }
         }
         return tiles;
      }).filter(tiles -> tiles.contains(end)).findAny().orElse(Collections.emptyList());
   }
}

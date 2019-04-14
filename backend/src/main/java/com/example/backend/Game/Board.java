package com.example.backend.Game;

import com.example.backend.Pieces.Piece;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public final class Board
{

   private List<Tile> tiles;
   private int with;
   private int height;

   public Board(int with, int height)
   {
      this.with = with;
      this.height = height;
   }

   public int getWith()
   {
      return with;
   }

   @SuppressWarnings("WeakerAccess")
   public int getHeight()
   {
      return height;
   }

   public Tile getTileAt(int x, int y)
   {
      return tiles.stream().filter(tile -> tile.getX() == x && tile.getY() == y).findFirst().orElse(null);
   }

   boolean movePiece(int xFrom, int yFrom, int xTo, int yTo, boolean qMove)
   {
      Optional<Piece> optionalPiece = getTileAt(xFrom, yFrom).getPiece();
      return optionalPiece.isPresent() && optionalPiece.get().move(getTileAt(xTo, yTo), qMove);
   }

   public void setTiles(List<Tile> tiles)
   {
      this.tiles = tiles;
   }

   Piece instantiatePiece(Player player, int x, int y, String pieceName)
   {
      Piece piece = null;
      try
      {
         Class<?> pieceClass = Class.forName("com.example.backend.Pieces." + pieceName);
         Constructor<?> constructor = pieceClass.getConstructor(Tile.class, Player.class);
         Tile tile = new Tile(x, y, this);
         piece = ((Piece) constructor.newInstance(tile, player));
         tile.setPiece(piece);
      }
      catch (NoSuchMethodException e)
      {
         System.out.println("Die Class auf die zugegriffen werden soll muss public sein");
      }
      catch (Exception ignored)
      {

      }
      return piece;
   }

   boolean falsifyXCoordinates(int... coordinates)
   {
      for (int coordinate : coordinates)
      {
         if (coordinate > with || coordinate < 0)
         {
            return true;
         }
      }
      return false;
   }

   boolean falsifyYCoordinates(int... coordinates)
   {
      for (int coordinate : coordinates)
      {
         if (coordinate > height || coordinate < 0)
         {
            return true;
         }
      }
      return false;
   }

   public enum Direction
   {

      TOP(0, 1),
      LEFT(-1, 0),
      RIGHT(1, 0),
      DOWN(0, -1),
      TOP_LEFT(-1, 1),
      TOP_RIGHT(1, 1),
      DOWN_LEFT(-1, -1),
      DOWN_RIGHT(1, -1);

      int x;
      int y;

      Direction(int x, int y)
      {
         this.x = x;
         this.y = y;
      }

      public static List<Direction> getDiagonals()
      {
         return Arrays.asList(TOP_LEFT, TOP_RIGHT, DOWN_LEFT, DOWN_RIGHT);
      }

      public static List<Direction> getOrthogonals()
      {
         return Arrays.asList(TOP, LEFT, DOWN, RIGHT);
      }
   }

   public static class Itr implements Iterator
   {
      private Tile pointer;
      private Direction direction;

      Itr(Tile pointer, Direction direction)
      {
         this.pointer = pointer;
         this.direction = direction;
      }

      @Override
      public boolean hasNext()
      {
         return pointer.getCloseOne(direction) != null;
      }

      @Override
      public Tile next()
      {
         pointer = pointer.getCloseOne(direction);
         return pointer;
      }
   }
}

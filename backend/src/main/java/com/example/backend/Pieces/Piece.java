package com.example.backend.Pieces;

import com.example.backend.Game.Player;
import com.example.backend.Game.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class Piece implements Cloneable
{
   public final static double MAX_STATUS = 1000000;

   protected Tile tile;
   private Player owner;
   private double status;
   //   private List<Piece> entangledPieces;
   private List<Piece> instances;

   private Predicate<Tile> qPredicate;
   private Predicate<Tile> nPredicate;

   /**
    * Extend this if you want a Piece that can move from point to point like
    * the king or the Knight. For movement in a Line see {@code InLine}
    *
    * @param tile  tile the Piece starts on
    * @param owner the owner
    * @see InLine
    */
   Piece(Tile tile, Player owner)
   {
      this.owner = owner;
      this.tile = tile;
      status = MAX_STATUS;
//      entangledPieces = new ArrayList<>();
      instances = new ArrayList<>();

      qPredicate = tile1 -> {
         if (tile1 == null)
         {
            return false;
         }
         if (!tile1.getPiece().isPresent())
         {
            return true;
         }
         else
         {
            return tile1.getPiece().get().status < MAX_STATUS;
         }
      };

      nPredicate = tile2 -> {
         if (tile2 == null)
         {
            return false;
         }
         if (!tile2.getPiece().isPresent())
         {
            return true;
         }
         else
         {
            return !tile2.getPiece().get().owner.equals(this.owner);
         }
      };
   }

   public boolean move(Tile newTile, boolean qMove)
   {
      if (getValidTiles(qMove).contains(newTile))
      {
         if (qMove)
         {
            List<Tile> entangledTiles = getEntangledTiles(newTile);
            List<Piece> entangledPieces = entangledTiles.stream().map(Tile::getPiece).filter(Optional::isPresent).map(Optional::get)
                  .collect(Collectors.toList());
            double newStatus = this.status * 0.5 *
                  entangledPieces.stream().mapToDouble(piece -> piece.status / MAX_STATUS).reduce(1, (left, right) -> right * left);
            if (newTile.getPiece().isPresent())
            {
               if (newStatus > Math.random() * MAX_STATUS)
               {
                  splitInstance(newTile, newStatus);
                  return true;
               }
               else
               {
                  discardInstance();
                  return false;
               }
            }
            splitInstance(newTile, newStatus);
         }
         else
         {
            newTile.getPiece().ifPresent(Piece::removeFromOwner);
            this.tile.setPiece(null);
            this.tile = newTile;
            newTile.setPiece(this);
         }
         return true;
      }
      return false;
   }

   private void splitInstance(Tile newTile, double newStatus)
   {
      Piece clonedPiece = duplicate();
      this.instances.add(clonedPiece);
      clonedPiece.instances.add(this);
      newTile.setPiece(clonedPiece);
      clonedPiece.tile = newTile;
      clonedPiece.setStatus(newStatus);
      this.setStatus(MAX_STATUS - clonedPiece.getStatus());
//            clonedPiece.entangledPieces.addAll(entangledPieces);
   }

   private void discardInstance()
   {
      if (instances.size() > 1)
      {
         for (Piece instance : instances)
         {
            instance.setStatus(instance.getStatus() + this.status / instances.size());
            instance.instances.remove(this);
         }
         this.tile.setPiece(null);
         this.removeFromOwner();
      }
   }


   List<Tile> getEntangledTiles(Tile newTile)
   {
      Tile entangledTile = null;
      double entangledValue = Double.MAX_VALUE;
      Set<Tile> firstMove = oneMove(tile, true);
      for (Tile tile1 : firstMove)
      {
         if (oneMove(tile1, true).contains(newTile) && tile1.getPiece().isPresent() &&
               !tile1.getPiece().get().owner.equals(this.owner) && tile1.getPiece().get().status < entangledValue)
         {
            entangledTile = tile1;
            entangledValue = tile1.getPiece().get().status;
         }
      }
      if (entangledTile != null)
      {
         return Collections.singletonList(entangledTile);
      }
      return Collections.emptyList();
   }

   public Set<Tile> getValidTiles(boolean isQuantumMove)
   {
      return isQuantumMove ? qMove(tile) : nMove(tile);
   }

   abstract Set<Tile> oneMove(Tile tile, boolean qMove);

   abstract Set<Tile> nMove(Tile tile);

   abstract Set<Tile> qMove(Tile tile);

   private void removeFromOwner()
   {
      owner.removePiece(this);
   }

   private void setStatus(double status)
   {
      tile.reportStatusChange();
      this.status = status;
   }

   public double getStatus()
   {
      return status;
   }

   public Tile getCurrentTile()
   {
      return tile;
   }

   @SuppressWarnings("WeakerAccess")
   public Player getOwner()
   {
      return owner;
   }

   Predicate<Tile> getQPredicate()
   {
      return qPredicate;
   }

   Predicate<Tile> getNPredicate()
   {
      return nPredicate;
   }

   private Piece duplicate()
   {
      Piece duplicate = clone();
      duplicate.instances = new ArrayList<>(this.instances);
      owner.addPiece(duplicate);
      return duplicate;
   }

   @Override
   public Piece clone()
   {
      try
      {
         return (Piece) super.clone();
      }
      catch (CloneNotSupportedException e)
      {
         e.printStackTrace();
      }
      return null;
   }
}
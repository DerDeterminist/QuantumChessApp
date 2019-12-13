package com.example.backend.Pieces;

import com.example.backend.Game.Player;
import com.example.backend.Game.Tile;
import com.example.backend.RandomGenerator;

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

   private String id;
   protected Tile tile;
   private Player owner;
   private double status;
   //   private List<Piece> entangledPieces;
   private List<Piece> instances; // one instance of this List for all instances Piece with the sam id

   private Predicate<Tile> qPredicate;
   private Predicate<Tile> nPredicate;

   /**
    * Extend this if you want a Piece that can move from point to point like
    * the King or the Knight. For movement in a Line see {@code InLine}
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
      instances.add(this);
      id = RandomGenerator.getString();

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
            return tile1.getPiece().get().getStatus() < MAX_STATUS;
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
            double newStatus = this.getStatus() * 0.5 *
                  entangledPieces.stream().mapToDouble(piece -> piece.getStatus() / MAX_STATUS)
                        .reduce(1, (left, right) -> right * left);
            splitInstance(newTile, newStatus);
         }
         else
         {
            if (status < MAX_STATUS)
            {
               if (measure())
               {
                  moveToNewTile(newTile);
               }
            }
            else
            {
               moveToNewTile(newTile);
            }
         }
         return true;
      }
      return false;
   }

   private void moveToNewTile(Tile newTile)
   {
      newTile.getPiece().ifPresent(piece -> {
         if (piece.getStatus() < MAX_STATUS)
         {
            piece.measure();
         }
         else
         {
            piece.removeFromOwner();
         }
      });
      this.tile.setPiece(null);
      this.tile = newTile;
      newTile.setPiece(null);
      newTile.setPiece(this);
   }

   private void splitInstance(Tile newTile, double newStatus)
   {
      Piece clonedPiece = duplicate();
      clonedPiece.setStatus(newStatus);
      newTile.setPiece(clonedPiece);
      clonedPiece.tile = newTile;
      newTile.reportStatusChange();
      this.setStatus(getStatus() - clonedPiece.getStatus());
      tile.reportStatusChange();
//            clonedPiece.entangledPieces.addAll(entangledPieces);
   }

   private boolean measure()
   {
      double random = Math.random() * MAX_STATUS;
      double counter = 0;
      Piece theOne = null;
      for (Piece instance : instances)
      {
         counter += instance.getStatus();
         if (counter >= random)
         {
            theOne = instance;
            break;
         }
      }
      assert theOne != null;
      theOne.setStatus(MAX_STATUS);
      theOne.tile.reportStatusChange();
      Piece finalTheOne = theOne;
      instances.stream().filter(piece -> !piece.equals(finalTheOne)).forEach(Piece::discard);
      instances.clear();
      return this.equals(theOne);
   }

   private void discard()
   {
      removeFromOwner();
      tile.setPiece(null);
      tile = null;
   }


   List<Tile> getEntangledTiles(Tile newTile)
   {
      Tile entangledTile = null;
      double entangledValue = Double.MAX_VALUE;
      Set<Tile> firstMove = oneMove(tile, true);
      for (Tile tile1 : firstMove)
      {
         if (oneMove(tile1, true).contains(newTile) && tile1.getPiece().isPresent() &&
               !tile1.getPiece().get().owner.equals(this.owner) && tile1.getPiece().get().getStatus() < entangledValue)
         {
            entangledTile = tile1;
            entangledValue = tile1.getPiece().get().getStatus();
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

   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   private Piece duplicate()
   {
      Piece duplicate = clone();
      instances.add(duplicate);
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
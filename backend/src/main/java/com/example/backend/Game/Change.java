package com.example.backend.Game;

import com.example.backend.Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class Change
{
   private final List<Piece> removed = new ArrayList<>();
   private final List<Piece> added = new ArrayList<>();
   private final List<Piece> changed = new ArrayList<>();

   public void addRemoved(Piece piece)
   {
      removed.add(piece.clone());
   }

   public void addAdded(Piece piece)
   {
      added.add(piece.clone());
   }

   public void addChanged(Piece piece)
   {
      changed.add(piece.clone());
   }

   public void clear()
   {
      removed.clear();
      added.clear();
      changed.clear();
   }

   public List<Piece> getRemoved()
   {
      return removed;
   }

   public List<Piece> getAdded()
   {
      return added;
   }

   public List<Piece> getChanged()
   {
      return changed;
   }
}

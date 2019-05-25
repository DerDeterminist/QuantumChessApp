package com.example.backend.Game;

import com.example.backend.Pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Change
{
   private final List<Piece> removed;
   private final List<Piece> added;
   private final List<Piece> changed;

   public Change()
   {
      removed = new ArrayList<>();
      added = new ArrayList<>();
      changed = new ArrayList<>();
   }

   public void addRemoved(Piece piece)
   {
      removed.add(piece);
   }

   public void addAdded(Piece piece)
   {
      added.add(piece);
   }

   public void addChanged(Piece piece)
   {
      changed.add(piece);
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

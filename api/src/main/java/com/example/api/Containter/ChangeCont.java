package com.example.api.Containter;

import com.example.backend.Game.Change;
import com.example.backend.Pieces.Piece;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ChangeCont
{
   private List<PieceCont> removed;
   private List<PieceCont> added;
   private List<PieceCont> changed;

   public ChangeCont(Change change)
   {
      this.removed = pieceToCont(change.getRemoved());
      this.added = pieceToCont(change.getAdded());
      this.changed = pieceToCont(change.getChanged());
   }

   private List<PieceCont> pieceToCont(List<Piece> list)
   {
      return list.stream().map(PieceCont::new).collect(Collectors.toList());
   }
}

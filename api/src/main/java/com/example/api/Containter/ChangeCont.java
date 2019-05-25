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
      this.removed = playerToCont(change.getRemoved());
      this.added = playerToCont(change.getAdded());
      this.changed = playerToCont(change.getChanged());
   }

   private List<PieceCont> playerToCont(List<Piece> list)
   {
      return list.stream().map(PieceCont::new).collect(Collectors.toList());
   }
}

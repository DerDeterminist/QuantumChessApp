package com.example.api.Containter;

import lombok.Data;

@Data
public class PieceCont
{
   private String id;
   private PieceType type;
   private PieceColor color;
   private double status;
   private int x;
   private int y;

   @Override
   public int hashCode() {
      return id.hashCode();
   }

   @Override
   public boolean equals(Object o)
   {
      if (o == null)
      {
         return false;
      }
      if (this == o)
      {
         return true;
      }
      if (!(o instanceof PieceCont))
      {
         return false;
      }
      return ((PieceCont) o).getId().equals(id);
   }
}
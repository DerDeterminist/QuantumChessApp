package com.example.api.Containter;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class BoardContainer
{
   private int with;
   private int height;
   private double maxPieceStatus;
   private Set<PieceContainer> pieces;

   public BoardContainer(int with, int height, double maxPieceStatus)
   {
      this.with = with;
      this.height = height;
      this.maxPieceStatus = maxPieceStatus;
      pieces = new HashSet<>();
   }

   public void addPieceEntity(PieceContainer pieceContainer)
   {
      pieces.add(pieceContainer);
   }

   public int getWith()
   {
      return with;
   }

   public void setWith(int with)
   {
      this.with = with;
   }

   public int getHeight()
   {
      return height;
   }

   public void setHeight(int height)
   {
      this.height = height;
   }

   public double getMaxPieceStatus()
   {
      return maxPieceStatus;
   }

   public void setMaxPieceStatus(double maxPieceStatus)
   {
      this.maxPieceStatus = maxPieceStatus;
   }

   public Set<PieceContainer> getPieces()
   {
      return pieces;
   }

   public void setPieces(Set<PieceContainer> pieces)
   {
      this.pieces = pieces;
   }
}
package com.example.api.Containter;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class BoardCont
{
   private int with;
   private int height;
   private double maxPieceStatus;
   private Set<PieceCont> pieces;

   // is necessary because the room lib dos not like lombok
   public BoardCont()
   {
   }

   public BoardCont(int with, int height, double maxPieceStatus)
   {
      this.with = with;
      this.height = height;
      this.maxPieceStatus = maxPieceStatus;
      pieces = new HashSet<>();
   }
}
package com.example.api.Containter;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class BoardCont
{
   private int with;
   private int height;
   private double maxPieceStatus;
   private Set<PieceCont> pieces;

   public BoardCont(int with, int height, double maxPieceStatus)
   {
      this.with = with;
      this.height = height;
      this.maxPieceStatus = maxPieceStatus;
      pieces = new HashSet<>();
   }
}
package com.example.api.containter;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
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
}
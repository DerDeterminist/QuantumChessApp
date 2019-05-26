package com.example.api.Containter;

import lombok.Data;

@Data
public class PieceCont
{
   private PieceType type;
   private PieceColor color;
   private double status;
   private int x;
   private int y;
}
package com.example.api.containter;

import lombok.Data;

@Data
public class PieceContainer
{
   private String type;
   private int owner;
   private double status;
   private int x;
   private int y;

   public PieceContainer(String type, int owner, double status, int x, int y)
   {
      this.type = type;
      this.owner = owner;
      this.status = status;
      this.x = x;
      this.y = y;
   }
}
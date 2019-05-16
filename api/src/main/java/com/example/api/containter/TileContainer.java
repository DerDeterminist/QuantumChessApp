package com.example.api.containter;

import lombok.Data;

@Data
public class TileContainer
{
   private int x;
   private int y;

   public TileContainer(int x, int y)
   {
      this.x = x;
      this.y = y;
   }
}
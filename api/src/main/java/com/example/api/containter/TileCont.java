package com.example.api.containter;

import lombok.Data;

@Data
public class TileCont
{
   private int x;
   private int y;

   public TileCont(int x, int y)
   {
      this.x = x;
      this.y = y;
   }
}
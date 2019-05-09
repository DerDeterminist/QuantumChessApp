package com.example.api.Containter;

@SuppressWarnings("unused")
public class TileContainer
{
   private int x;
   private int y;

   public TileContainer(int x, int y)
   {
      this.x = x;
      this.y = y;
   }

   public int getX()
   {
      return x;
   }

   public void setX(int x)
   {
      this.x = x;
   }

   public int getY()
   {
      return y;
   }

   public void setY(int y)
   {
      this.y = y;
   }
}
package com.example.api.Containter;

@SuppressWarnings({"unused"})
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

   public String getType()
   {
      return type;
   }

   public void setType(String type)
   {
      this.type = type;
   }

   public int getOwner()
   {
      return owner;
   }

   public void setOwner(int owner)
   {
      this.owner = owner;
   }

   public double getStatus()
   {
      return status;
   }

   public void setStatus(double status)
   {
      this.status = status;
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
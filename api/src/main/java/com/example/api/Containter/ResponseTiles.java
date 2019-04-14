package com.example.api.Containter;

import com.example.backend.Game.ResponseStatus;

import java.util.Set;

@SuppressWarnings("unused")
public class ResponseTiles
{
   private Set<TileContainer> tiles;
   private ResponseStatus status;

   public ResponseTiles(Set<TileContainer> tiles, ResponseStatus status)
   {
      this.tiles = tiles;
      this.status = status;
   }

   public Set<TileContainer> getTiles()
   {
      return tiles;
   }

   public void setTiles(Set<TileContainer> tiles)
   {
      this.tiles = tiles;
   }

   public ResponseStatus getStatus()
   {
      return status;
   }

   public void setStatus(ResponseStatus status)
   {
      this.status = status;
   }
}
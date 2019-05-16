package com.example.api.containter;

import lombok.Data;

import java.util.Set;

@Data
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
}
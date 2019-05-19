package com.example.api.containter;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class TileResponse extends AbstractResponse
{
   private Set<TileCont> tiles;

   public TileResponse(Set<TileCont> tiles, StatusCont status)
   {
      this.tiles = tiles;
      setStatus(status);
   }
}
package com.example.api.Response;

import com.example.api.containter.StatusCont;
import com.example.api.containter.TileCont;
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
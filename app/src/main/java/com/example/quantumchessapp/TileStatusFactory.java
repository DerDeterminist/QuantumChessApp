package com.example.quantumchessapp;

import com.example.api.Containter.TileCont;
import com.example.api.Response.TileResponse;
import com.example.quantumchessapp.conts.Tile;
import com.example.quantumchessapp.conts.TileStatus;

import java.util.stream.Collectors;

class TileStatusFactory
{
   static TileStatus createTileStatus(TileResponse response)
   {
      TileStatus tileStatus = new TileStatus();
      tileStatus.setTiles(response.getTiles().stream().map(TileStatusFactory::createTile).collect(Collectors.toSet()));
      return tileStatus;
   }

   private static Tile createTile(TileCont tileCont)
   {
      Tile tile = new Tile();
      tile.setX(tileCont.getX());
      tile.setY(GameManager.convertY(tileCont.getY()));
      return tile;
   }
}

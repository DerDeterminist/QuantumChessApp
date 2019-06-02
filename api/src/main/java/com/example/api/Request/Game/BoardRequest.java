package com.example.api.Request.Game;

public class BoardRequest extends GameRequest
{
   @Override
   public String getUrl()
   {
      return GAME_BASE_URL + "/completeBord";
   }
}

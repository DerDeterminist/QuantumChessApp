package com.example.api.Request.Game;

public class StartRequest extends GameRequest
{
   @Override
   public String getUrl()
   {
      return GAME_BASE_URL + "/start";
   }
}

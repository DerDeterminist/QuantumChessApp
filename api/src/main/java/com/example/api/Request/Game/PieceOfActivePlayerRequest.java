package com.example.api.Request.Game;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PieceOfActivePlayerRequest extends GameRequest
{
   private int x;
   private int y;

   @Override
   public String getUrl()
   {
      return GAME_BASE_URL + "/isPieceOfActivePlayer";
   }
}

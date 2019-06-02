package com.example.api.Request.Game;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PossibleMovesRequest extends GameRequest
{
   private int xFrom;
   private int yFrom;
   private boolean qMove;

   @Override
   public String getUrl()
   {
      return GAME_BASE_URL + "/possibleMoves";
   }
}

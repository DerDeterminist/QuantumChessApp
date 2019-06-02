package com.example.api.Request.Game;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MovePieceRequest extends GameRequest
{
   private int xFrom;
   private int yFrom;
   private int xTo;
   private int yTo;
   private boolean qMove;

   @Override
   public String getUrl()
   {
      return GAME_BASE_URL + "/movePiece";
   }
}

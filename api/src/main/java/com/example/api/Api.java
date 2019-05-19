package com.example.api;

import com.example.api.containter.BoardResponse;
import com.example.api.containter.StatusResponse;
import com.example.api.containter.TileResponse;

public interface Api
{
   static Api getInstance(GameVariant variant)
   {
      switch (variant)
      {
         case ONLINE:
            return new OnlineAPI();
         case OFFLINE:
            return new OfflineAPI();
            default:
               throw new RuntimeException("every GameVariant has to be implemented");
      }
   }

   String startGame();

   TileResponse getPossibleMoves(String gameID, int xFrom, int yFrom, boolean qMove);

   StatusResponse movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove);

   BoardResponse getCompleteBord(String gameID);

   boolean isPieceOfActivePlayer(String gameID, int x, int y);
}
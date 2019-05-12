package com.example.api;

import com.example.api.Containter.ResponseContent;
import com.example.api.Containter.ResponseStatus;
import com.example.api.Containter.ResponseTiles;

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

   ResponseTiles getPossibleMoves(String gameID, int xFrom, int yFrom, boolean qMove);

   ResponseStatus movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove);

   ResponseContent getCompleteBord(String gameID);

   boolean isPieceOfActivePlayer(String gameID, int x, int y);
}
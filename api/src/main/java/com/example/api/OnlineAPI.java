package com.example.api;

import com.example.api.containter.ResponseContent;
import com.example.api.containter.ResponseStatus;
import com.example.api.containter.ResponseTiles;

class OnlineAPI implements Api
{
   @Override
   public String startGame()
   {
      return "";
   }

   @Override
   public ResponseTiles getPossibleMoves(String gameID, int xFrom, int yFrom, boolean qMove)
   {
      return null;
   }

   @Override
   public ResponseStatus movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove)
   {
      return null;
   }

   @Override
   public ResponseContent getCompleteBord(String gameID)
   {
      return null;
   }

   @Override
   public boolean isPieceOfActivePlayer(String gameID, int x, int y)
   {
      return false;
   }
}

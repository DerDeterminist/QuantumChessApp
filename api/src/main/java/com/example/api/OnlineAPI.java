package com.example.api;

import com.example.api.Response.BoardResponse;
import com.example.api.Response.PieceOfActivePlayerResponse;
import com.example.api.Response.StatusResponse;
import com.example.api.Response.TileResponse;

class OnlineAPI implements Api
{
   @Override
   public String startGame()
   {
      return "";
   }

   @Override
   public TileResponse getPossibleMoves(String gameID, int xFrom, int yFrom, boolean qMove)
   {
      return null;
   }

   @Override
   public StatusResponse movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove)
   {
      return null;
   }

   @Override
   public BoardResponse getCompleteBord(String gameID)
   {
      return null;
   }

   @Override
   public PieceOfActivePlayerResponse isPieceOfActivePlayer(String gameID, int x, int y)
   {
      return null;
   }
}

package com.example.api;

import com.example.api.Response.BoardResponse;
import com.example.api.Response.ChangeResponse;
import com.example.api.Response.PieceOfActivePlayerResponse;
import com.example.api.Response.StartResponse;
import com.example.api.Response.TileResponse;

public interface Api
{
   StartResponse startGame();

   TileResponse getPossibleMoves(String gameID, int xFrom, int yFrom, boolean qMove);

   ChangeResponse movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove);

   BoardResponse getCompleteBord(String gameID);

   PieceOfActivePlayerResponse isPieceOfActivePlayer(String gameID, int x, int y);
}
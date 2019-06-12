package com.example.api;

import com.example.api.Response.BoardResponse;
import com.example.api.Response.ChangeResponse;
import com.example.api.Response.PieceOfActivePlayerResponse;
import com.example.api.Response.StartResponse;
import com.example.api.Response.TileResponse;

/**
 * defines the LocaleAPI, GameClient(rest client), GameManager(app)
 *
 * @see LocaleAPI
 */
public interface Api
{
   /**
    * Starts a new Game
    *
    * @return the gameID
    */
   StartResponse startGame();

   /**
    * @param gameID id of the game
    * @param xFrom start x
    * @param yFrom start y
    * @param qMove is quantum move?
    * @return List of all possible TileCont
    * @see com.example.api.Containter.TileCont
    */
   TileResponse getPossibleMoves(String gameID, int xFrom, int yFrom, boolean qMove);

   /**
    * @param gameID id of the game
    * @param xFrom start x
    * @param yFrom start y
    * @param xTo new x
    * @param yTo new y
    * @param qMove is quantum move?
    * @return everything that changed
    */
   ChangeResponse movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove);

   /**
    * @param gameID id of the game
    * @return attributes of the board
    */
   BoardResponse getCompleteBord(String gameID);

   PieceOfActivePlayerResponse isPieceOfActivePlayer(String gameID, int x, int y);
}
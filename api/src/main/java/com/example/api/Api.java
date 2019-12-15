package com.example.api;

import com.example.api.Response.GameStateResponse;
import com.example.api.Response.PieceOfActivePlayerResponse;
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
    * @return the state of the game
    */
   GameStateResponse startGame();

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
    * @return the state of the game
    */
   GameStateResponse movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove);

   /**
    * @param gameID id of the game
    * @return the state of the game
    */
   GameStateResponse getCompleteBord(String gameID);

   PieceOfActivePlayerResponse isPieceOfActivePlayer(String gameID, int x, int y);
}
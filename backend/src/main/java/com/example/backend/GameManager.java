package com.example.backend;

import com.example.backend.Game.Board;
import com.example.backend.Game.Change;
import com.example.backend.Game.DefaultGameImpl;
import com.example.backend.Game.Game;
import com.example.backend.Game.Player;
import com.example.backend.Game.ResponseStatus;
import com.example.backend.Game.Tile;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages all the Games played on this backend.
 * Is multithreading save
 *
 * @see Game
 */
public class GameManager
{
   private volatile static ConcurrentHashMap<String, Game> games = new ConcurrentHashMap<>();
   private final static GameManager gameManager = new GameManager();

   private GameManager()
   {
   }

   public synchronized static GameManager getInstance()
   {
      return gameManager;
   }

   public String startGame()
   {
      String gameID = RandomGenerator.getString();
      games.put(gameID, new DefaultGameImpl(new Player(0), new Player(1)));
      return gameID;
   }

   public Set<Tile> getPossibleMoves(String gameID, int xFrom, int yFrom, boolean qMove)
   {
      return games.containsKey(gameID) ? games.get(gameID).getPossibleMoves(xFrom, yFrom, qMove) : Collections.emptySet();
   }

   public Change movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove)
   {
      if (gameID != null && games.containsKey(gameID) && games.get(gameID) != null)
      {
         games.get(gameID).movePiece(xFrom, yFrom, xTo, yTo, qMove);
         return games.get(gameID).getChange();
      }
      return null;
   }

   public ResponseStatus getStatus(String gameID)
   {
      return games.get(gameID).getStatus();
   }

   public Board getBoard(String gameID)
   {
      return games.get(gameID).getCompleteBord();
   }

   public boolean isPieceOfActivePlayer(String gameID, int x, int y)
   {
      return games.get(gameID).isPieceOfActivePlayer(x, y);
   }

   public Change getChange(String gameID)
   {
      return games.get(gameID).getChange();
   }
}

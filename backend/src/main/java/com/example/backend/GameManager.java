package com.example.backend;

import com.example.backend.Game.Board;
import com.example.backend.Game.DefaultGameImpl;
import com.example.backend.Game.Game;
import com.example.backend.Game.Player;
import com.example.backend.Game.ResponseStatus;
import com.example.backend.Game.Tile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class GameManager
{
   private volatile static HashMap<String, Game> games = new HashMap<>();

   public String startGame()
   {
      String gameID = UUID.randomUUID().toString();
      games.put(gameID, new DefaultGameImpl(new Player(), new Player()));
      return gameID;
   }

   public Set<Tile> getPossibleMoves(String gameID, int xFrom, int yFrom, boolean qMove)
   {
      return games.containsKey(gameID) ? games.get(gameID).getPossibleMoves(xFrom, yFrom, qMove) : Collections.emptySet();
   }

   public ResponseStatus movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove)
   {
      if (gameID != null && games.containsKey(gameID) && games.get(gameID) != null)
      {
         games.get(gameID).movePiece(xFrom, yFrom, xTo, yTo, qMove);
         return games.get(gameID).getStatus();
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
}

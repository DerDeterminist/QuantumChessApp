package com.example.api.Containter;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ResponseStatus
{
   private boolean lastMoveWasValid;
   private boolean isGameWon;
   private int winner;
   private int activePlayer;
   private List<Integer> loser;

   public ResponseStatus(com.example.backend.Game.ResponseStatus responseStatus)
   {
      isGameWon = responseStatus.isGameWon();
      winner = responseStatus.getWinner();
      lastMoveWasValid = responseStatus.isLastMoveWasValid();
      activePlayer = responseStatus.getActivePlayer();
      loser = new ArrayList<>(responseStatus.getLoser());
   }

   public List<Integer> getLoser()
   {
      return loser;
   }

   public void setLoser(List<Integer> loser)
   {
      this.loser = loser;
   }

   public boolean isLastMoveWasValid()
   {
      return lastMoveWasValid;
   }

   public void setLastMoveWasValid(boolean lastMoveWasValid)
   {
      this.lastMoveWasValid = lastMoveWasValid;
   }

   public boolean isGameWon()
   {
      return isGameWon;
   }

   public void setGameWon(boolean gameWon)
   {
      isGameWon = gameWon;
   }

   public int getWinner()
   {
      return winner;
   }

   public void setWinner(int winner)
   {
      this.winner = winner;
   }

   public int getActivePlayer()
   {
      return activePlayer;
   }

   public void setActivePlayer(int activePlayer)
   {
      this.activePlayer = activePlayer;
   }
}

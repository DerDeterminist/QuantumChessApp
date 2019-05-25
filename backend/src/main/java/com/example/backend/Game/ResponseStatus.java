package com.example.backend.Game;

import java.util.List;

@SuppressWarnings("unused")
public class ResponseStatus
{
   private boolean lastMoveWasValid = false;
   private boolean isGameWon;
   private int winner;
   private int activePlayer;
   private List<Integer> loser;

   /**
    * State of the game. Winner will be -1 if the game is not won jet.
    * @param isGameWon is the game won?
    * @param winner    id of the winner
    * @param loser     ids of the losers
    * @param activePlayer id of the active player
    */
   ResponseStatus(boolean isGameWon, int winner, List<Integer> loser, int activePlayer)
   {
      this.isGameWon = isGameWon;
      this.winner = winner;
      this.loser = loser;
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

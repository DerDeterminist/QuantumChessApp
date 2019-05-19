package com.example.api.containter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StatusCont
{
   private boolean lastMoveWasValid;
   private boolean isGameWon;
   private int winner;
   private int activePlayer;
   private List<Integer> loser;

   public StatusCont(com.example.backend.Game.ResponseStatus responseStatus)
   {
      isGameWon = responseStatus.isGameWon();
      winner = responseStatus.getWinner();
      lastMoveWasValid = responseStatus.isLastMoveWasValid();
      activePlayer = responseStatus.getActivePlayer();
      loser = new ArrayList<>(responseStatus.getLoser());
   }
}

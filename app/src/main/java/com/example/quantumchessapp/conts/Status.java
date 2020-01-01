package com.example.quantumchessapp.conts;

import java.util.List;

import lombok.Data;

@Data
public class Status
{
   private boolean lastMoveValid;
   private boolean isGameWon;
   private int winner;
   private int activePlayer;
   private List<Integer> loser;
}

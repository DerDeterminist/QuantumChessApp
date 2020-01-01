package com.example.quantumchessapp.conts;

import lombok.Data;

@Data
public class GameState
{
   private String gameID;
   private Change change;
   private Board board;
   private Status status;
}

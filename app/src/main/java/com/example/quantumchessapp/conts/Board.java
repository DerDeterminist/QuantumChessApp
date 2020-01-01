package com.example.quantumchessapp.conts;

import java.util.Set;

import lombok.Data;

@Data
public class Board
{
   private int with;
   private int height;
   private double maxPieceStatus;
   private Set<Piece> pieces;
}

package com.example.quantumchessapp.conts;

import java.util.List;

import lombok.Data;

@Data
public class Change
{
   private List<Piece> removed;
   private List<Piece> added;
   private List<Piece> changed;
}

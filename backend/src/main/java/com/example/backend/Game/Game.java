package com.example.backend.Game;

import java.util.Set;

public interface Game
{
   Set<Tile> getPossibleMoves(int xFrom, int yFrom, boolean qMove);

   void movePiece(int xFrom, int yFrom, int xTo, int yTo, boolean qMove);

   Board getCompleteBord();

   ResponseStatus getStatus();

   boolean isPieceOfActivePlayer(int x, int y);
}
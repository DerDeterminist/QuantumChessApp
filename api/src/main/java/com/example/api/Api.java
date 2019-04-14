package com.example.api;

import com.example.api.Containter.BoardContainer;
import com.example.api.Containter.ResponseContent;
import com.example.api.Containter.ResponseTiles;
import com.example.api.Containter.TileContainer;
import com.example.backend.Game.Board;
import com.example.backend.Game.ResponseStatus;
import com.example.backend.Game.Tile;
import com.example.backend.GameManager;
import com.example.backend.Pieces.Piece;

import java.util.Set;
import java.util.stream.Collectors;

public class Api
{
   private static volatile GameManager gameManager = new GameManager();

   public String startGame()
   {
      return gameManager.startGame();
   }

   public ResponseTiles getPosibleMoves(String gameID, int xFrom, int yFrom, boolean qMove)
   {
      Set<Tile> possibleMoves = gameManager.getPossibleMoves(gameID, xFrom, yFrom, qMove);
      return new ResponseTiles(tileToTileEntity(possibleMoves), gameManager.getStatus(gameID));
   }

   public ResponseStatus movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove)
   {
      return gameManager.movePiece(gameID, xFrom, yFrom, xTo, yTo, qMove);
   }

   public ResponseContent getCompleteBord(String gameID)
   {
      Board board = gameManager.getBoard(gameID);
      return new ResponseContent(new BoardContainer(board.getWith(), board.getHeight(), Piece.MAX_STATUS),
            gameManager.getStatus(gameID));
   }

   private Set<TileContainer> tileToTileEntity(Set<Tile> tiles)
   {
      return tiles.stream().map(tile -> new TileContainer(tile.getX(), tile.getY())).collect(Collectors.toSet());
   }
}
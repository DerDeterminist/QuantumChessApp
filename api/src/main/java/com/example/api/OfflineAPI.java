package com.example.api;

import com.example.api.containter.BoardContainer;
import com.example.api.containter.ResponseContent;
import com.example.api.containter.ResponseStatus;
import com.example.api.containter.ResponseTiles;
import com.example.api.containter.TileContainer;
import com.example.backend.Game.Board;
import com.example.backend.Game.Tile;
import com.example.backend.GameManager;
import com.example.backend.Pieces.Piece;

import java.util.Set;
import java.util.stream.Collectors;

class OfflineAPI implements Api
{
   private static volatile GameManager gameManager = GameManager.getInstance();

   OfflineAPI()
   {
   }

   @Override
   public String startGame()
   {
      return gameManager.startGame();
   }

   @Override
   public ResponseTiles getPossibleMoves(String gameID, int xFrom, int yFrom, boolean qMove)
   {
      Set<Tile> possibleMoves = gameManager.getPossibleMoves(gameID, xFrom, yFrom, qMove);
      return new ResponseTiles(tileToTileEntity(possibleMoves), new ResponseStatus(gameManager.getStatus(gameID)));
   }

   @Override
   public ResponseStatus movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove)
   {
      return new ResponseStatus(gameManager.movePiece(gameID, xFrom, yFrom, xTo, yTo, qMove));
   }

   @Override
   public ResponseContent getCompleteBord(String gameID)
   {
      Board board = gameManager.getBoard(gameID);
      return new ResponseContent(new BoardContainer(board.getWith(), board.getHeight(), Piece.MAX_STATUS),
            gameManager.getStatus(gameID));
   }

   @Override
   public boolean isPieceOfActivePlayer(String gameID, int x, int y)
   {
      return gameManager.isPieceOfActivePlayer(gameID, x, y);
   }

   private Set<TileContainer> tileToTileEntity(Set<Tile> tiles)
   {
      return tiles.stream().map(tile -> new TileContainer(tile.getX(), tile.getY())).collect(Collectors.toSet());
   }
}
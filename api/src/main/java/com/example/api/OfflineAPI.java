package com.example.api;

import com.example.api.containter.BoardCont;
import com.example.api.containter.BoardResponse;
import com.example.api.containter.StatusCont;
import com.example.api.containter.StatusResponse;
import com.example.api.containter.TileCont;
import com.example.api.containter.TileResponse;
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
   public TileResponse getPossibleMoves(String gameID, int xFrom, int yFrom, boolean qMove)
   {
      Set<Tile> possibleMoves = gameManager.getPossibleMoves(gameID, xFrom, yFrom, qMove);
      return new TileResponse(tileToTileEntity(possibleMoves), new StatusCont(gameManager.getStatus(gameID)));
   }

   @Override
   public StatusResponse movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove)
   {
      return new StatusResponse(new StatusCont(gameManager.movePiece(gameID, xFrom, yFrom, xTo, yTo, qMove)));
   }

   @Override
   public BoardResponse getCompleteBord(String gameID)
   {
      Board board = gameManager.getBoard(gameID);
      return new BoardResponse(new BoardCont(board.getWith(), board.getHeight(), Piece.MAX_STATUS),
            gameManager.getStatus(gameID));
   }

   @Override
   public boolean isPieceOfActivePlayer(String gameID, int x, int y)
   {
      return gameManager.isPieceOfActivePlayer(gameID, x, y);
   }

   private Set<TileCont> tileToTileEntity(Set<Tile> tiles)
   {
      return tiles.stream().map(tile -> new TileCont(tile.getX(), tile.getY())).collect(Collectors.toSet());
   }
}
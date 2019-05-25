package com.example.api;

import com.example.api.Containter.BoardCont;
import com.example.api.Containter.ChangeCont;
import com.example.api.Containter.StatusCont;
import com.example.api.Containter.TileCont;
import com.example.api.Response.BoardResponse;
import com.example.api.Response.ChangeResponse;
import com.example.api.Response.PieceOfActivePlayerResponse;
import com.example.api.Response.TileResponse;
import com.example.backend.Game.Board;
import com.example.backend.Game.Change;
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
      TileResponse tileResponse = new TileResponse();
      tileResponse.setTiles(tileToTileEntity(possibleMoves));
      tileResponse.setStatus(new StatusCont(gameManager.getStatus(gameID)));
      return tileResponse;
   }

   @Override
   public ChangeResponse movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove)
   {
      gameManager.movePiece(gameID, xFrom, yFrom, xTo, yTo, qMove);
      StatusCont statusCont = new StatusCont(gameManager.getStatus(gameID));
      Change change = gameManager.getChange(gameID);

      ChangeResponse changeResponse = new ChangeResponse();
      changeResponse.setChangeCont(new ChangeCont(change));
      changeResponse.setStatus(statusCont);
      return changeResponse;
   }

   @Override
   public BoardResponse getCompleteBord(String gameID)
   {
      Board board = gameManager.getBoard(gameID);
      BoardResponse boardResponse = new BoardResponse();
      boardResponse.setBoardCont(new BoardCont(board.getWith(), board.getHeight(), Piece.MAX_STATUS));
      boardResponse.setStatus(new StatusCont(gameManager.getStatus(gameID)));
      return boardResponse;
   }

   @Override
   public PieceOfActivePlayerResponse isPieceOfActivePlayer(String gameID, int x, int y)
   {
      PieceOfActivePlayerResponse pieceOfActivePlayerResponse = new PieceOfActivePlayerResponse();
      pieceOfActivePlayerResponse.setPieceOfActivePlayer(gameManager.isPieceOfActivePlayer(gameID, x, y));
      pieceOfActivePlayerResponse.setStatus(new StatusCont(gameManager.getStatus(gameID)));
      return pieceOfActivePlayerResponse;
   }

   private Set<TileCont> tileToTileEntity(Set<Tile> tiles)
   {
      return tiles.stream().map(tile -> new TileCont(tile.getX(), tile.getY())).collect(Collectors.toSet());
   }
}
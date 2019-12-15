package com.example.api;

import com.example.api.Containter.BoardCont;
import com.example.api.Containter.ChangeCont;
import com.example.api.Containter.PieceColor;
import com.example.api.Containter.PieceCont;
import com.example.api.Containter.PieceType;
import com.example.api.Containter.StatusCont;
import com.example.api.Containter.TileCont;
import com.example.api.Response.GameStateResponse;
import com.example.api.Response.PieceOfActivePlayerResponse;
import com.example.api.Response.TileResponse;
import com.example.backend.Game.Board;
import com.example.backend.Game.Change;
import com.example.backend.Game.Tile;
import com.example.backend.GameManager;
import com.example.backend.Pieces.Piece;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Api for Backend Module
 * @see GameManager
 */
public class LocaleAPI implements Api
{
   private static volatile GameManager gameManager = GameManager.getInstance();

   @Override
   public GameStateResponse startGame()
   {
      String gameID = gameManager.startGame();

      return createGameStateResponse(gameID);
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
   public GameStateResponse movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove)
   {
      gameManager.movePiece(gameID, xFrom, yFrom, xTo, yTo, qMove);

      return createGameStateResponse(gameID);
   }

   @Override
   public GameStateResponse getCompleteBord(String gameID)
   {
      return createGameStateResponse(gameID);
   }

   @Override
   public PieceOfActivePlayerResponse isPieceOfActivePlayer(String gameID, int x, int y)
   {
      PieceOfActivePlayerResponse pieceOfActivePlayerResponse = new PieceOfActivePlayerResponse();
      pieceOfActivePlayerResponse.setPieceOfActivePlayer(gameManager.isPieceOfActivePlayer(gameID, x, y));
      pieceOfActivePlayerResponse.setStatus(new StatusCont(gameManager.getStatus(gameID)));
      return pieceOfActivePlayerResponse;
   }

   private GameStateResponse createGameStateResponse(String gameID)
   {
      GameStateResponse response = new GameStateResponse();
      response.setGameID(gameID);

      Change change = gameManager.getChange(gameID);

      ChangeCont changeCont = new ChangeCont();
      changeCont.setAdded(pieceToCont(change.getAdded()));
      changeCont.setRemoved(pieceToCont(change.getRemoved()));
      changeCont.setChanged(pieceToCont(change.getChanged()));
      response.setChangeCont(changeCont);

      Board board = gameManager.getBoard(gameID);
      response.setBoardCont(new BoardCont(board.getWith(), board.getHeight(), Piece.MAX_STATUS));

      StatusCont statusCont = new StatusCont(gameManager.getStatus(gameID));
      response.setStatus(statusCont);

      return response;
   }

   private List<PieceCont> pieceToCont(List<Piece> pieces)
   {
      return pieces.stream().map(piece -> {
         PieceCont pieceCont = new PieceCont();
         pieceCont.setType(PieceType.valueOf(piece.getClass().getSimpleName().toUpperCase()));
         pieceCont.setStatus(piece.getStatus());
         pieceCont.setX(piece.getCurrentTile().getX());
         pieceCont.setY(piece.getCurrentTile().getY());
         pieceCont.setId(piece.getId());
         switch (piece.getOwner().getId())
         {
            case 0:
               pieceCont.setColor(PieceColor.WHITE);
               break;
            case 1:
               pieceCont.setColor(PieceColor.BLACK);
               break;
         }
         return pieceCont;
      }).collect(Collectors.toList());
   }

   private Set<TileCont> tileToTileEntity(Set<Tile> tiles)
   {
      return tiles.stream().map(tile -> new TileCont(tile.getX(), tile.getY())).collect(Collectors.toSet());
   }
}
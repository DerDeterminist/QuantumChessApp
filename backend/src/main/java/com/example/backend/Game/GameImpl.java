package com.example.backend.Game;

import com.example.backend.Pieces.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class GameImpl implements Game
{
   private Board board;
   private List<Player> players = new ArrayList<>();
   private String pieceToWin = "King";
   private ResponseStatus lastResponseStatus = null;
   private Player activePlayer;

   /**
    * If you extend this you need to instantiate the {@code Piece}s and build the Bord
    *
    * @param boardWith   with of the board
    * @param boardHeight height of the board
    * @see BordBuilder
    * @see Piece
    */
   GameImpl(int boardWith, int boardHeight, Player... players)
   {
      if (players.length < 1)
      {
         throw new RuntimeException("A Game must at least have one Player");
      }
      this.players.addAll(Arrays.asList(players));
      activePlayer = players[0];
      this.board = new Board(boardWith, boardHeight);
   }

   public Board getBoard()
   {
      return board;
   }

   @Override
   public Set<Tile> getPossibleMoves(int xFrom, int yFrom, boolean qMove)
   {
      if (board.falsifyXCoordinates(xFrom) || board.falsifyYCoordinates(yFrom))
      {
         return Collections.emptySet();
      }
      lastResponseStatus = createStatus();
      Optional<Piece> piece = board.getTileAt(xFrom, yFrom).getPiece();
      return piece.isPresent() ? piece.get().getValidTiles(qMove) : Collections.emptySet();
   }

   @Override
   public void movePiece(int xFrom, int yFrom, int xTo, int yTo, boolean qMove)
   {
      if (board.falsifyXCoordinates(xFrom, xTo) || board.falsifyYCoordinates(yFrom, yTo))
      {
         lastResponseStatus = createStatus();
         lastResponseStatus.setLastMoveWasValid(false);
      }
      boolean lastMoveWasValid = board.movePiece(xFrom, yFrom, xTo, yTo, qMove);
      if (lastMoveWasValid)
      {
         updateActivePlayer();
      }
      lastResponseStatus = createStatus();
      lastResponseStatus.setLastMoveWasValid(lastMoveWasValid);
   }

   @Override
   public Board getCompleteBord()
   {
      lastResponseStatus = createStatus();
      return board;
   }

   @Override
   public boolean isPieceOfActivePlayer(int x, int y)
   {
      return activePlayer.getPieces().stream().anyMatch(
            piece -> piece.getCurrentTile().getX() == x && piece.getCurrentTile().getY() == y);
   }

   private void updateActivePlayer()
   {
      int indexActivePlayer = players.indexOf(activePlayer);
      activePlayer = players.get(indexActivePlayer < players.size() - 1 ? ++indexActivePlayer : 0);
   }

   @Override
   public ResponseStatus getStatus()
   {
      return lastResponseStatus;
   }

   @Override
   public String getFEN()
   {
      if (players.size() != 2)
      {
         throw new RuntimeException("To generate a Forsyth–Edwards Notation a game needs 2 player");
      }

      StringBuilder stringBuilder = new StringBuilder();

      for (int y = 0; y < 8; y++)
      {
         int emptyTilesInARow = 0;
         for (int x = 0; x < 8; x++)
         {
            Optional<Piece> piece = board.getTileAt(x, y).getPiece();

            if (piece.isPresent())
            {
               // some empty fields
               if (emptyTilesInARow != 0)
               {
                  stringBuilder.append(emptyTilesInARow);
                  emptyTilesInARow = 0;
               }

               String name = piece.get().getClass().getSimpleName();
               char character;
               switch (name)
               {
                  case "Bishop":
                     character = 'b';
                     break;
                  case "King":
                     character = 'k';
                     break;
                  case "Knight":
                     character = 'n';
                     break;
                  case "Pawen":
                     character = 'p';
                     break;
                  case "Queen":
                     character = 'q';
                     break;
                  case "Rook":
                     character = 'r';
                     break;
                  default:
                     throw new RuntimeException("New Pieces have to be implemented here");
               }
               if (piece.get().getOwner().getId() == 0)// White is uppercase
               {
                  character = Character.toUpperCase(character);
               }
               stringBuilder.append(character);
            }
            else
            {
               emptyTilesInARow++;
            }
         }

         // empty row
         if (emptyTilesInARow != 0)
         {
            stringBuilder.append(emptyTilesInARow);
         }
         stringBuilder.append("/");
      }
      return stringBuilder.toString();
   }

   private ResponseStatus createStatus()
   {
      long anzPiecesToWin = players.stream().flatMap(player -> player.getPieces().stream()).filter(
            piece -> piece.getClass().getSimpleName().equals(pieceToWin)).count();
      int winner = -1;
      List<Player> leftPlayers = players.stream().filter(
            player -> player.getPieces().stream().anyMatch(piece -> piece.getClass().getSimpleName().equals(pieceToWin)))
            .collect(Collectors.toList());
      if (leftPlayers.size() == 1)
      {
         winner = players.indexOf(leftPlayers.get(0));
      }
      return new ResponseStatus(anzPiecesToWin == 1, winner, players.stream().filter(player -> !leftPlayers.contains(player)).map(
            player -> players.indexOf(player)).collect(Collectors.toList()), players.indexOf(activePlayer));
   }

   @Override
   public Change getChange()
   {
      createStatus();
      return board.getChange();
   }
}

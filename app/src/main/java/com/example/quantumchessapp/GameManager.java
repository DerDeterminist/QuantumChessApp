package com.example.quantumchessapp;

import com.example.api.Api;
import com.example.api.Containter.BoardContainer;
import com.example.api.Containter.ResponseStatus;
import com.example.api.Containter.ResponseTiles;
import com.example.quantumchessapp.figuren.Piece;
import com.example.quantumchessapp.spiel.Player;
import com.example.quantumchessapp.spiel.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class GameManager
{
   private static String gameID;
   private static Api api = new Api();

   private static List<Player> players;
   private static boolean isGameWon;
   private static boolean lastMoveWasValid;
   private static Player winner;

   private static int wight;
   private static int height;
   private static double maxPieceStatus;

   public static void newGame(Player player, Player player1)
   {
      gameID = api.startGame();
      players = new ArrayList<>();
      players.add(player);
      players.add(player1);

      BoardContainer boardContainer = api.getCompleteBord(gameID).getBoardContainer();
      height = boardContainer.getHeight();
      wight = boardContainer.getWith();
      maxPieceStatus = boardContainer.getMaxPieceStatus();
   }

   public static List<Position> getPossibleMoves(Piece piece, boolean qMove)
   {
      Position startPosition = piece.getPosition();
      ResponseTiles possibleMoves = api.getPossibleMoves(gameID, startPosition.getX(), startPosition.getY(), qMove);
      convertStatus(possibleMoves.getStatus());
      return possibleMoves.getTiles().stream().map(tileContainer -> new Position(tileContainer.getX(), tileContainer.getY()))
            .collect(Collectors.toList());
   }

   public static void movePiece(Piece piece, Position toMoveTo, boolean qMove)
   {
      Position startPosition = piece.getPosition();
      convertStatus(api.movePiece(gameID, startPosition.getX(), startPosition.getY(), toMoveTo.getX(), toMoveTo.getY(), qMove));
   }

   private static void convertStatus(final ResponseStatus responseStatus)
   {
      isGameWon = responseStatus.isGameWon();
      lastMoveWasValid = responseStatus.isLastMoveWasValid();
      winner = players.stream().filter(player -> player.getId() == responseStatus.getWinner()).findAny().orElse(null);
   }

   public static List<Player> getPlayers()
   {
      return players;
   }

   public static boolean isGameWon()
   {
      return isGameWon;
   }

   public static boolean isLastMoveValid()
   {
      return lastMoveWasValid;
   }

   public static Player getWinner()
   {
      return winner;
   }

   public static int getWight()
   {
      return wight;
   }

   public static int getHeight()
   {
      return height;
   }

   public static double getMaxPieceStatus()
   {
      return maxPieceStatus;
   }
}

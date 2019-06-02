package com.example.quantumchessapp;

import android.os.AsyncTask;
import com.example.api.Api;
import com.example.api.Containter.BoardCont;
import com.example.api.Containter.ChangeCont;
import com.example.api.Containter.StatusCont;
import com.example.api.GameVariant;
import com.example.api.Response.AbstractResponse;
import com.example.api.Response.BoardResponse;
import com.example.api.Response.ChangeResponse;
import com.example.api.Response.PieceOfActivePlayerResponse;
import com.example.api.Response.TileResponse;
import com.example.quantumchessapp.spiel.Player;
import com.example.quantumchessapp.spiel.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class GameManager
{
   private static String gameID; // TODO: 19.05.2019 RuntimeException handling
   private static Api api;

   private static List<Player> players;
   private static boolean isGameWon;
   private static boolean lastMoveWasValid;
   private static Player winner;

   private static int wight;
   private static int height;
   private static double maxPieceStatus;

   public static void newGame(Player player, Player player1, GameVariant variance)
   {
      api = Api.getInstance(variance);
      players = new ArrayList<>();
      players.add(player);
      players.add(player1);
      BoardCont boardCont = ((BoardResponse) doAsync(() -> {
         gameID = api.startGame().getGameID();
         return api.getCompleteBord(gameID);
      })).getBoardCont();
      height = boardCont.getHeight();
      wight = boardCont.getWith();
      maxPieceStatus = boardCont.getMaxPieceStatus();
   }

   public static List<Position> getPossibleMoves(Position startPosition, boolean qMove)
   {
      TileResponse tileResponse = (TileResponse) doAsync(() -> {
         TileResponse possibleMoves =
               api.getPossibleMoves(gameID, convertPositionWight(startPosition), convertPositionHeight(startPosition), qMove);
         convertStatus(possibleMoves.getStatus());
         return possibleMoves;
      });
      return tileResponse.getTiles().stream()
            .map(tileContainer -> convertXYToPosition(tileContainer.getX(), tileContainer.getY()))
            .collect(Collectors.toList());
   }

   public static ChangeCont movePiece(Position startPosition, Position toMoveToPosition, boolean qMove)
   {
      ChangeResponse changeResponse = (ChangeResponse) doAsync(() -> null);
      convertStatus(changeResponse.getStatus());
      ChangeCont changeCont = changeResponse.getChangeCont();
      changeCont.getAdded().forEach(cont -> cont.setY(height - 1 - cont.getY()));
      changeCont.getRemoved().forEach(cont -> cont.setY(height - 1 - cont.getY()));
      changeCont.getChanged().forEach(cont -> cont.setY(height - 1 - cont.getY()));
      return changeCont;
   }

   public static boolean isPieceOfActivePlayer(Position position)
   {
      return ((PieceOfActivePlayerResponse) doAsync(
            () -> api.isPieceOfActivePlayer(gameID, convertPositionWight(position), convertPositionHeight(position))))
            .isPieceOfActivePlayer();
   }

   private static AbstractResponse doAsync(Supplier<AbstractResponse> todo)
   {
      AsyncTask<Object, Object, AbstractResponse> task = new AsyncTask<Object, Object, AbstractResponse>()
      {
         @Override
         protected AbstractResponse doInBackground(Object... objects)
         {
            return todo.get();
         }
      };
      task.execute();
      try
      {
         return task.get();
      }
      catch (ExecutionException | InterruptedException e)
      {
         throw new RuntimeException(e);
      }
   }

   private static void convertStatus(final StatusCont statusCont)
   {
      isGameWon = statusCont.isGameWon();
      lastMoveWasValid = statusCont.isLastMoveWasValid();
      winner = players.stream().filter(player -> player.getId() == statusCont.getWinner()).findAny().orElse(null);
   }

   private static Position convertXYToPosition(int x, int y)
   {
      return new Position(x, height - 1 - y);
   }

   private static int convertPositionHeight(Position position)
   {
      return height - 1 - position.getY();
   }

   private static int convertPositionWight(Position position)
   {
      return position.getX();
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

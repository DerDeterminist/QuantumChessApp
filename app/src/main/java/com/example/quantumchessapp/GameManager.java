package com.example.quantumchessapp;

import android.content.Context;
import com.example.api.Api;
import com.example.api.Containter.BoardCont;
import com.example.api.Containter.ChangeCont;
import com.example.api.Containter.StatusCont;
import com.example.api.GameModel;
import com.example.api.LocaleAPI;
import com.example.api.Response.ChangeResponse;
import com.example.api.Response.TileResponse;
import com.example.restapi.GameClient;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Manages the currently played game.
 * Bundles the communication the different Apis.
 * Depending on the Api has to change the gameModel itself of takes advantage of the observerPattern
 *
 * @see LocaleAPI
 * @see GameClient
 */
public class GameManager
{
   private static Api api;
   private static GameClient client;
   private static GameModel model;
   private static GameVariant variant;

   public static void newGame(Context context, GameVariant variant)
   {
      model = new GameModel();
      GameManager.variant = variant;
      switch (variant)
      {
         case OFFLINE:
            api = new LocaleAPI();
            model.setPlayers(Collections.emptyList());
            model.setGameID(api.startGame().getGameID());
            BoardCont boardCont = api.getCompleteBord(model.getGameID()).getBoardCont();
            model.setHeight(boardCont.getHeight());
            model.setWight(boardCont.getWith());
            model.setMaxPieceStatus(boardCont.getMaxPieceStatus());
            break;
         case ONLINE:
            client = new GameClient(context, model);
            client.startGame();
            model.addPropertyChangeListener("start", evt -> client.getCompleteBord((String) evt.getNewValue()));
            break;
      }
   }

   public static void getPossibleMoves(Position startPosition, boolean qMove)
   {
      switch (variant)
      {
         case OFFLINE:
            TileResponse possibleMoves =
                  api.getPossibleMoves(model.getGameID(), convertPositionWight(startPosition), convertPositionHeight(startPosition),
                        qMove);
            convertStatus(possibleMoves.getStatus());
            model.setPossibleMoves(new ArrayList<>(possibleMoves.getTiles()));
            break;
         case ONLINE:
            client.getPossibleMoves(model.getGameID(), convertPositionWight(startPosition), convertPositionHeight(startPosition),
                  qMove);
            break;
      }
   }

   public static void movePiece(Position startPosition, Position toMoveToPosition, boolean qMove)
   {
      switch (variant)
      {
         case OFFLINE:
            ChangeResponse changeResponse =
                  api.movePiece(model.getGameID(), convertPositionWight(startPosition), convertPositionHeight(startPosition),
                        convertPositionWight(toMoveToPosition), convertPositionHeight(toMoveToPosition), qMove);
            convertStatus(changeResponse.getStatus());
            ChangeCont changeCont = changeResponse.getChangeCont();
            changeCont.getAdded().forEach(cont -> cont.setY(model.getHeight() - 1 - cont.getY()));
            changeCont.getRemoved().forEach(cont -> cont.setY(model.getHeight() - 1 - cont.getY()));
            changeCont.getChanged().forEach(cont -> cont.setY(model.getHeight() - 1 - cont.getY()));
            model.setChange(changeCont);
            break;
         case ONLINE:
            client.movePiece(model.getGameID(), convertPositionWight(startPosition), convertPositionHeight(startPosition),
                  convertPositionWight(toMoveToPosition), convertPositionHeight(toMoveToPosition), qMove);
            break;
      }
   }

   public static void isPieceOfActivePlayer(Position position)
   {
      switch (variant)
      {
         case OFFLINE:
            model.setPieceOfActivePlayer(
                  api.isPieceOfActivePlayer(model.getGameID(), convertPositionWight(position), convertPositionHeight(position))
                        .isPieceOfActivePlayer());
            break;
         case ONLINE:
            client.isPieceOfActivePlayer(model.getGameID(), convertPositionWight(position), convertPositionHeight(position));
            break;
      }
   }

   private static void convertStatus(final StatusCont statusCont)
   {
      model.setGameWon(statusCont.isGameWon());
      model.setLastMoveWasValid(statusCont.isLastMoveValid());
   }

   public static Position convertXYToPosition(int x, int y)
   {
      return new Position(x, model.getHeight() - 1 - y);
   }

   private static int convertPositionHeight(Position position)
   {
      return model.getHeight() - 1 - position.getY();
   }

   private static int convertPositionWight(Position position)
   {
      return position.getX();
   }

   public static GameModel getModel()
   {
      return model;
   }

   public static void addPropertyChangeListener(String property, PropertyChangeListener listener)
   {
      model.addPropertyChangeListener(property, listener);
   }

   @SuppressWarnings("unused")
   public static void removePropertyChangeListener(PropertyChangeListener listener)
   {
      model.removePropertyChangeListener(listener);
   }
}

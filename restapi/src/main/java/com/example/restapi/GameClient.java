package com.example.restapi;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.api.Containter.BoardCont;
import com.example.api.Containter.StatusCont;
import com.example.api.GameModel;
import com.example.api.Request.Game.BoardRequest;
import com.example.api.Request.Game.GameRequest;
import com.example.api.Request.Game.MovePieceRequest;
import com.example.api.Request.Game.PieceOfActivePlayerRequest;
import com.example.api.Request.Game.PossibleMovesRequest;
import com.example.api.Request.Game.StartRequest;
import com.example.api.Response.AbstractResponse;
import com.example.api.Response.BoardResponse;
import com.example.api.Response.ChangeResponse;
import com.example.api.Response.PieceOfActivePlayerResponse;
import com.example.api.Response.StartResponse;
import com.example.api.Response.TileResponse;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * RestApi oriented at Api.
 * uses observablePattern to fill the GameModel
 *
 * @see com.example.api.Api
 * @see GameModel
 */
public class GameClient
{
   private static Gson gson = new Gson();
   private static RequestQueue queue;
   private static GameModel model;

   public GameClient(Context context, GameModel model)
   {
      queue = Volley.newRequestQueue(context);
      GameClient.model = model;
   }

   public void startGame()
   {
      final StartRequest startRequest = new StartRequest();
      request(startRequest, StartResponse.class,
            (Consumer<StartResponse>) startResponse -> model.setGameID(startResponse.getGameID()));
   }

   public void getPossibleMoves(String gameID, int xFrom, int yFrom, boolean qMove)
   {
      PossibleMovesRequest request = new PossibleMovesRequest();
      request.setGameID(gameID);
      request.setQMove(qMove);
      request.setXFrom(xFrom);
      request.setYFrom(yFrom);
      request.setGameID(gameID);
      request(request, TileResponse.class, (Consumer<TileResponse>) tileResponse -> {
         convertStatus(tileResponse.getStatus());
         model.setPossibleMoves(new ArrayList<>(tileResponse.getTiles()));
      });
   }

   public void movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove)
   {
      MovePieceRequest request = new MovePieceRequest();
      request.setGameID(gameID);
      request.setXFrom(xFrom);
      request.setYFrom(yFrom);
      request.setXTo(xTo);
      request.setYTo(yTo);
      request.setQMove(qMove);
      request(request, ChangeResponse.class, (Consumer<ChangeResponse>) changeResponse -> {
         convertStatus(changeResponse.getStatus());
         model.setChange(changeResponse.getChangeCont());
      });
   }

   public void getCompleteBord(String gameID)
   {
      BoardRequest request = new BoardRequest();
      request.setGameID(gameID);
      request(request, BoardResponse.class, (Consumer<BoardResponse>) boardResponse -> {
         BoardCont boardCont = boardResponse.getBoardCont();
         model.setHeight(boardCont.getHeight());
         model.setWight(boardCont.getWith());
         model.setMaxPieceStatus(boardCont.getMaxPieceStatus());
      });
   }

   public void isPieceOfActivePlayer(String gameID, int x, int y)
   {
      PieceOfActivePlayerRequest request = new PieceOfActivePlayerRequest();
      request.setX(x);
      request.setY(y);
      request.setGameID(gameID);
      request(request, PieceOfActivePlayerResponse.class,
            (Consumer<PieceOfActivePlayerResponse>) pieceOfActivePlayerResponse -> model
                  .setPieceOfActivePlayer(pieceOfActivePlayerResponse.isPieceOfActivePlayer()));
   }

   private static void convertStatus(final StatusCont statusCont)
   {
      model.setGameWon(statusCont.isGameWon());
      model.setLastMoveWasValid(statusCont.isLastMoveValid());
      model.setWinner(
            model.getPlayers().stream().filter(player -> player.getId() == statusCont.getWinner()).findAny().orElse(null));
   }

   private static void request(GameRequest gameRequest, final Class<? extends AbstractResponse> aClass,
                               Consumer consumer)
   {
      try
      {
         final JsonObjectRequest request =
               new JsonObjectRequest(Request.Method.POST, gameRequest.getUrl(), new JSONObject(gson.toJson(gameRequest)),
                     response -> {
                        AbstractResponse response1 = gson.fromJson(response.toString(), aClass);
                        //noinspection unchecked
                        consumer.accept(response1);
                     }, Throwable::printStackTrace);
         queue.add(request);
      }
      catch (JSONException e)
      {
         e.printStackTrace();
      }
   }
}

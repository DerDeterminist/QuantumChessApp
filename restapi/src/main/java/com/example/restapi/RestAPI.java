package com.example.restapi;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.api.Api;
import com.example.api.Request.AbstractRequest;
import com.example.api.Request.PlayerRequest;
import com.example.api.Response.BoardResponse;
import com.example.api.Response.ChangeResponse;
import com.example.api.Response.PieceOfActivePlayerResponse;
import com.example.api.Response.TileResponse;
import org.json.JSONArray;

public class RestAPI implements Api
{
   private RequestQueue queue;
   private Context context;

   public RestAPI(Context context)
   {
      this.context = context;
   }

   @Override
   public String startGame()
   {
      queue = Volley.newRequestQueue(context);

      AbstractRequest abstractRequest = new PlayerRequest();
      request(abstractRequest);
      return null;
   }

   @Override
   public TileResponse getPossibleMoves(String gameID, int xFrom, int yFrom, boolean qMove)
   {
      return null;
   }

   @Override
   public ChangeResponse movePiece(String gameID, int xFrom, int yFrom, int xTo, int yTo, boolean qMove)
   {
      return null;
   }

   @Override
   public BoardResponse getCompleteBord(String gameID)
   {
      return null;
   }

   @Override
   public PieceOfActivePlayerResponse isPieceOfActivePlayer(String gameID, int x, int y)
   {
      return null;
   }

   private void request(final AbstractRequest abstractRequest)
   {
      // TODO: 02.06.2019 send request
      JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, abstractRequest.getUrl(), null,
            new Response.Listener<JSONArray>()
            {
               @Override
               public void onResponse(JSONArray response)
               {
                  System.out.println();
               }
            }, new Response.ErrorListener()
      {
         @Override
         public void onErrorResponse(VolleyError error)
         {
            error.printStackTrace();
         }
      });

      queue.add(request);
   }
}

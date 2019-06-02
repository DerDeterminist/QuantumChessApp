package com.example.api.Request.Game;

import com.example.api.Request.AbstractRequest;
import lombok.Data;

@Data
public abstract class GameRequest extends AbstractRequest
{
   protected static final String GAME_BASE_URL = "http://10.0.2.2:8080/api/v1/game";
   private String gameID;
}

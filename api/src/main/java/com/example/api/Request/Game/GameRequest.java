package com.example.api.Request.Game;

import com.example.api.Request.AbstractRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * All requests about the game functionality have to extend this
 */
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class GameRequest extends AbstractRequest
{
   static final String GAME_BASE_URL = "http://10.0.2.2:8080/api/v1/game";
   private String gameID;
}

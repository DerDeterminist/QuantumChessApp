package com.example.api.Request;

import com.example.api.Containter.PlayerCont;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Example request for a player
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PlayerRequest extends AbstractRequest
{
   private final static String URL = "http://10.0.2.2:8080/api/v1/Players";

   private PlayerCont playerCont;

   @Override
   public String getUrl()
   {
      return URL;
   }
}

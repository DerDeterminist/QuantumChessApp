package com.example.api.containter;

import lombok.Data;

public class PlayerContainer
{
   @Data
   public class Player
   {
      private int id;
      private String userID;
      private String userName;
      private int elo = 1200;
      private boolean showUserNameOnline;
   }
}

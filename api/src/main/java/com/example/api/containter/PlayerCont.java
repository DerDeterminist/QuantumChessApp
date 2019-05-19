package com.example.api.containter;

import lombok.Data;

@Data
public class PlayerCont
{
      private int id;
      private String userID;
      private String userName;
      private int elo = 1200;
      private boolean showUserNameOnline;
}

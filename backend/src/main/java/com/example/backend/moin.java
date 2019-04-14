package com.example.backend;

import com.example.backend.Game.DefaultGameImpl;
import com.example.backend.Game.Player;

public class moin
{
   public static void main(String[] args)
   {
      Player playerWhite = new Player();
      Player playerBlack = new Player();
      new DefaultGameImpl(playerWhite, playerBlack);
      System.out.println("GameImpl done well");
   }
}

package com.example.backend;

import com.example.backend.Game.DefaultGameImpl;
import com.example.backend.Game.Player;

public class moin
{
   public static void main(String[] args)
   {
      Player playerWhite = new Player(0);
      Player playerBlack = new Player(1);
      new DefaultGameImpl(playerWhite, playerBlack);
      System.out.println("GameImpl done well");
   }
}

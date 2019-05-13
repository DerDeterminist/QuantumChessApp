package com.example.bootstrap;

import com.example.domain.Player;
import com.example.respositories.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner
{

   private final PlayerRepository playerRepository;

   public BootstrapData(PlayerRepository playerRepository)
   {
      this.playerRepository = playerRepository;
   }

   @Override
   public void run(String... args) throws Exception
   {
      System.out.println("loading TestData");

      Player player = new Player();
      player.setUserID("asdkfjhasd");
      player.setShowUserNameOnline(true);
      player.setUserName("Karl");
      playerRepository.save(player);

      Player player1 = new Player();
      player1.setShowUserNameOnline(false);
      player1.setUserID("shgurgag");
      player1.setUserName("Karl asdf");
      player1.setElo(6854);
      playerRepository.save(player1);

      Player player2 = new Player();
      player2.setShowUserNameOnline(true);
      player2.setUserID("asdhfasdf");
      player2.setUserName("Karl Meier");
      player2.setElo(684);
      playerRepository.save(player2);

      Player player3 = new Player();
      player3.setShowUserNameOnline(false);
      player3.setUserID("brhtourt");
      player3.setUserName("Karl Mai");
      player3.setElo(64);
      playerRepository.save(player3);

      System.out.println("Players saved: " + playerRepository.count());
   }
}

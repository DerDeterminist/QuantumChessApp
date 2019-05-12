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
      playerRepository.save(player);

      Player player1 = new Player();
      playerRepository.save(player1);

      Player player2 = new Player();
      playerRepository.save(player2);

      Player player3 = new Player();
      playerRepository.save(player3);

      System.out.println("Players saved: " + playerRepository.count());
   }
}

package com.example.Service;

import com.example.domain.Player;
import com.example.respositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class PlayerServiceImpl implements PlayerService
{

   private final PlayerRepository playerRepository;

   public PlayerServiceImpl(PlayerRepository playerRepository)
   {
      this.playerRepository = playerRepository;
   }

   @Override
   public Player findPlayerByID(Integer id)
   {
      return playerRepository.findById(id).orElse(null);
   }

   @Override
   public List<Player> findAllPlayers()
   {
      return playerRepository.findAll();
   }

   @Override
   public Player savePlayer(Player player)
   {
      return playerRepository.save(player);
   }
}

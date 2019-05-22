package com.example.Service;

import com.example.domain.Player;

import java.util.List;

public interface PlayerService
{
   Player findPlayerByID(Integer id);

   List<Player> findAllPlayers();

   Player savePlayer(Player player);

   Player findPlayerByUserID(String id);
}

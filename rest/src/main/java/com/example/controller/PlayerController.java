package com.example.controller;

import com.example.Service.PlayerService;
import com.example.api.Containter.PlayerCont;
import com.example.api.Request.PlayerRequest;
import com.example.api.Response.PlayerResponse;
import com.example.domain.Player;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@RestController
@RequestMapping(PlayerController.BASE_URL)
public class PlayerController
{
   static final String BASE_URL = "/api/v1/Players";

   private final PlayerService playerService;

   public PlayerController(PlayerService playerService)
   {
      this.playerService = playerService;
   }

   @GetMapping
   public List<PlayerResponse> getAllPlayers()
   {
      return playerService.findAllPlayers().stream().map(this::playerToPlayerResponse).collect(Collectors.toList());
   }

   @GetMapping("/{id}")
   public PlayerResponse getPlayerById(@PathVariable Integer id)
   {
      return playerToPlayerResponse(playerService.findPlayerByID(id));
   }

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public PlayerResponse savePlayer(@RequestBody PlayerRequest request)
   {
      return playerToPlayerResponse(playerService.savePlayer(playerContToPlayer(request.getPlayerCont())));
   }

   @GetMapping("/userID/{userID}")
   public PlayerResponse getPlayerByUserID(@PathVariable String userID)
   {
      return playerToPlayerResponse(playerService.findPlayerByUserID(userID));
   }

   private PlayerResponse playerToPlayerResponse(Player player)
   {
      PlayerCont cont = new PlayerCont();
      cont.setId(player.getId());
      cont.setUserID(player.getUserID());
      cont.setElo(player.getElo());
      cont.setUserName(player.getUserName());
      cont.setShowUserNameOnline(player.isShowUserNameOnline());

      PlayerResponse response = new PlayerResponse();
      response.setPlayerCont(cont);
      return response;
   }

   private Player playerContToPlayer(PlayerCont cont)
   {
      Player player = new Player();
      player.setId(cont.getId());
      player.setUserID(cont.getUserID());
      player.setElo(cont.getElo());
      player.setUserName(cont.getUserName());
      player.setShowUserNameOnline(player.isShowUserNameOnline());
      return player;
   }
}

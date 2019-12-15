package com.example.api.Response;

import com.example.api.Containter.BoardCont;
import com.example.api.Containter.ChangeCont;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GameStateResponse extends AbstractResponse
{
   private String gameID;
   private ChangeCont changeCont;// todo remove later
   private BoardCont boardCont;
}

package com.example.api.Response;

import com.example.api.containter.PlayerCont;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlayerResponse extends AbstractResponse
{
   private PlayerCont playerCont;
}

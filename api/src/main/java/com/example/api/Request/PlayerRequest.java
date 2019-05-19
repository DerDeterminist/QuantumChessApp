package com.example.api.Request;

import com.example.api.containter.PlayerCont;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlayerRequest extends AbstractRequest
{
   private PlayerCont playerCont;
}

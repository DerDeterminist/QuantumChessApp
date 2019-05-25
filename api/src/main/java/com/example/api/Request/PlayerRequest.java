package com.example.api.Request;

import com.example.api.Containter.PlayerCont;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlayerRequest extends AbstractRequest
{
   private PlayerCont playerCont;
}

package com.example.api.containter;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlayerResponse extends AbstractResponse
{
   private PlayerCont playerCont;
}

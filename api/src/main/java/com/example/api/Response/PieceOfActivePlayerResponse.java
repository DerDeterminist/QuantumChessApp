package com.example.api.Response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PieceOfActivePlayerResponse extends AbstractResponse
{
   private boolean pieceOfActivePlayer;
}
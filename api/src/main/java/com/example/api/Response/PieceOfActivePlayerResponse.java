package com.example.api.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class PieceOfActivePlayerResponse extends AbstractResponse
{
   private boolean pieceOfActivePlayer;
}
package com.example.api.Response;

import com.example.api.Containter.TileCont;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class TileResponse extends AbstractResponse
{
   private Set<TileCont> tiles;
}
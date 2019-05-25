package com.example.api.Response;

import com.example.api.Containter.BoardCont;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BoardResponse extends AbstractResponse
{
   private BoardCont boardCont;
}
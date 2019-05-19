package com.example.api.Response;

import com.example.api.containter.StatusCont;

public class StatusResponse extends AbstractResponse
{
   public StatusResponse(StatusCont status)
   {
      setStatus(status);
   }
}

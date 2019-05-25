package com.example.api.Response;

import com.example.api.Containter.StatusCont;
import lombok.Data;

@Data
public abstract class AbstractResponse
{
   private StatusCont status;
}

package com.example.api.Response;

import com.example.api.containter.StatusCont;
import lombok.Data;

@Data
public abstract class AbstractResponse
{
   private StatusCont status;
}

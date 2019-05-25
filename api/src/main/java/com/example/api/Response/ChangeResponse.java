package com.example.api.Response;

import com.example.api.Containter.ChangeCont;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChangeResponse extends AbstractResponse
{
   private ChangeCont changeCont;
}

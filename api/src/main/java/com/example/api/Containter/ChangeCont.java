package com.example.api.Containter;

import lombok.Data;

import java.util.List;

@Data
public class ChangeCont
{
   private List<PieceCont> removed;
   private List<PieceCont> added;
   private List<PieceCont> changed;
}

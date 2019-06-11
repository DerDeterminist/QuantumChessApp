package com.example.backend;

import java.util.Random;

public class RandomGenerator
{
   public static String getString()
   {
      String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
      StringBuilder salt = new StringBuilder();
      Random rnd = new Random();
      while (salt.length() < 18)
      {
         int index = rnd.nextInt(SALTCHARS.length());
         salt.append(SALTCHARS.charAt(index));
      }
      return salt.toString();
   }
}
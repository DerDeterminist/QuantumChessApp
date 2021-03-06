package com.example.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Player
{
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   private String userID;
   private String userName;
   private int elo = 1200;
   private boolean showUserNameOnline;
}

package com.example.quantumchessapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

public class Account
{
   private static Account account;

   private final static String FILE_NAME = "accData";
   private final static String USERID = "userid";
   private final static String USERNAME = "username";
   private final static String SHOW_ONLINE = "showonline";

   private String userID;
   private String userName;
   private boolean showUserNameOnline;

   private static SharedPreferences sharedPreferences;

   public static Account getInstance()
   {
      return account;
   }

   public Account(Context context)
   {
      sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
      account = this;
   }

   public void loadAccountData()
   {
      sharedPreferences.getString(USERID, String.valueOf(UUID.randomUUID()));
      sharedPreferences.getString(USERNAME, "noName");
      sharedPreferences.getBoolean(SHOW_ONLINE, true);
   }

   public void saveAccountData()
   {
      SharedPreferences.Editor edit = sharedPreferences.edit();
      edit.putString(USERNAME, userName);
      edit.putString(USERNAME, userName);
      edit.putBoolean(SHOW_ONLINE, showUserNameOnline);
      edit.apply();
   }

   public boolean hasAccount()
   {
      return sharedPreferences.contains(USERID);
   }

   public String getUserID()
   {
      return userID;
   }

   public void setUserID(String userID)
   {
      this.userID = userID;
   }

   public String getUserName()
   {
      return userName;
   }

   public void setUserName(String userName)
   {
      this.userName = userName;
   }

   public boolean isShowUserNameOnline()
   {
      return showUserNameOnline;
   }

   public void setShowUserNameOnline(boolean showUserNameOnline)
   {
      this.showUserNameOnline = showUserNameOnline;
   }
}

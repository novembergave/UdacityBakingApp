package com.novembergave.bakingapp.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import android.content.Context;

import com.novembergave.bakingapp.pojo.Recipe;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class JsonUtils {

  public static List<Recipe> parseBakingList(Context context) {
    GsonBuilder builder = new GsonBuilder();
    Gson gson = builder.create();
    Type listType = new TypeToken<List<Recipe>>() {}.getType();
    return gson.fromJson(getJson(context), listType);
  }

  private static String getJson(Context context) {
    String json;
    try {
      InputStream inputStream = context.getAssets().open("baking.json");
      int size = inputStream.available();
      byte[] buffer = new byte[size];
      inputStream.read(buffer);
      inputStream.close();
      json = new String(buffer, "UTF-8");
    } catch (IOException ex) {
      ex.printStackTrace();
      return null;
    }
    return json;
  }
}

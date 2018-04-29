package com.novembergave.bakingapp.utils;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.novembergave.bakingapp.pojo.Recipe;

import static com.novembergave.bakingapp.utils.FormatUtils.isEmpty;

public class SharedPreferencesUtils {

  private static final String KEY = "shared_prefs";
  private static final String PREF_SELECTED_RECIPE = "pref_selected_recipe";

  private static SharedPreferences getSharedPreferences(Context context) {
    return context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
  }

  public static void updateSelectedRecipe(Context context, @NonNull Recipe recipe) {
    String convertedRecipe = new Gson().toJson(recipe);
    getSharedPreferences(context).edit().putString(PREF_SELECTED_RECIPE, convertedRecipe).apply();
  }

  public static Recipe getSelectedRecipe(Context context) {
    String convertedRecipe = getSharedPreferences(context).getString(PREF_SELECTED_RECIPE, null);
    if (isEmpty(convertedRecipe)) {
      return null;
    } else {
      return new Gson().fromJson(convertedRecipe, Recipe.class);
    }
  }
}

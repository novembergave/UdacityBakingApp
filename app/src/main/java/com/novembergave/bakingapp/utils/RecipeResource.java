package com.novembergave.bakingapp.utils;

import com.novembergave.bakingapp.pojo.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeResource {
  @GET("baking.json")
  Call<List<Recipe>> getRecipe();
}

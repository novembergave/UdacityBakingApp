package com.novembergave.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.novembergave.bakingapp.pojo.Recipe;
import com.novembergave.bakingapp.recyclerviews.mainactivity.MainAdapter;
import com.novembergave.bakingapp.utils.RecipeResource;
import com.novembergave.bakingapp.utils.RetrofitBuilder;
import com.novembergave.bakingapp.utils.SharedPreferencesUtils;
import com.novembergave.bakingapp.widget.WidgetService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  private static final String CLASS = MainActivity.class.getName();
  private static final String STATE_RECIPES = CLASS + ".state_recipes";
  private static final String EXTRA_RECIPE = CLASS + ".extra_recipe";

  private View errorView;
  private Button retryButton;
  private RecyclerView recyclerView;
  private MainAdapter adapter;
  private List<Recipe> recipes;

  public static Intent launchRecipeFromWidget(Context context, Recipe recipe) {
    Intent intent = new Intent(context, MainActivity.class);
    intent.putExtra(EXTRA_RECIPE, recipe);
    return intent;
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    // Convert to ArrayList to save state
    if (recipes != null) {
      ArrayList<Recipe> recipes = new ArrayList<>();
      recipes.addAll(this.recipes);
      outState.putParcelableArrayList(STATE_RECIPES, recipes);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // if we're launching from widget, immediately open the recipe
    if (getIntent().getParcelableExtra(EXTRA_RECIPE) != null) {
      startActivity(RecipeActivity.launchActivity(this, getIntent().getParcelableExtra(EXTRA_RECIPE)));
    }

    errorView = findViewById(R.id.error_view);
    retryButton = findViewById(R.id.error_retry_button);
    retryButton.setOnClickListener(click -> fetchRecipes());

    recyclerView = findViewById(R.id.main_recycler_view);
    // Change how the recyclerView is displayed depending on the orientation
    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
    } else {
      recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }
    adapter = new MainAdapter(this::openActivity);
    recyclerView.setAdapter(adapter);

    if (savedInstanceState != null) {
      recipes = savedInstanceState.getParcelableArrayList(STATE_RECIPES);
      if (recipes != null) {
        adapter.setData(recipes);
      } else {
        errorView.setVisibility(View.VISIBLE);
      }
    } else {
      fetchRecipes();
    }
  }

  private void fetchRecipes() {
    RecipeResource resource = RetrofitBuilder.retrieve();
    Call<List<Recipe>> recipe = resource.getRecipe();

    recipe.enqueue(new Callback<List<Recipe>>() {
      @Override
      public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        errorView.setVisibility(View.GONE);
        recipes = response.body();
        adapter.setData(recipes);
      }

      @Override
      public void onFailure(Call<List<Recipe>> call, Throwable t) {
        Log.v("error fetching http: ", t.getMessage());
        errorView.setVisibility(View.VISIBLE);
      }
    });
  }

  private void openActivity(Recipe recipe) {
    // save to sharedPref first then update widget before launching activity
    SharedPreferencesUtils.updateSelectedRecipe(this, recipe);
    WidgetService.updateWidget(this);
    startActivity(RecipeActivity.launchActivity(this, recipe));
  }

}

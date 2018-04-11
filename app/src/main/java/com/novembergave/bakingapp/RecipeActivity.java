package com.novembergave.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.novembergave.bakingapp.pojo.Ingredient;
import com.novembergave.bakingapp.pojo.Step;
import com.novembergave.bakingapp.recyclerviews.recipeactivity.IngredientsAdapter;
import com.novembergave.bakingapp.recyclerviews.recipeactivity.StepsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipeActivity extends AppCompatActivity {

  private static final String CLASS = RecipeActivity.class.getName();
  private static final String EXTRA_NAME = CLASS + ".extra_name";

  public static Intent launchActivity(Context context, String name) {
    Intent intent = new Intent(context, RecipeActivity.class);
    intent.putExtra(EXTRA_NAME, name);
    return intent;
  }

  private RecyclerView ingredientsRecyclerView;
  private IngredientsAdapter ingredientsAdapter;
  private RecyclerView stepsRecyclerView;
  private StepsAdapter stepsAdapter;

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        this.finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe);
    setTitle(getIntent().getStringExtra(EXTRA_NAME));
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    ingredientsRecyclerView = findViewById(R.id.ingredient_recycler_view);
    ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    ingredientsAdapter = new IngredientsAdapter();
    ingredientsRecyclerView.setAdapter(ingredientsAdapter);
    ingredientsAdapter.setData(generateDummyIngredientList());

    stepsRecyclerView = findViewById(R.id.steps_recycler_view);
    stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    stepsAdapter = new StepsAdapter(this::openActivity);
    stepsRecyclerView.setAdapter(stepsAdapter);
    stepsAdapter.setData(generateDummyStepsList());
  }

  private void openActivity(String url) {
    startActivity(ViewRecipeStepActivity.launchActivity(this, url));
  }

  private List<Ingredient> generateDummyIngredientList() {
    List<Ingredient> ingredients = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      ingredients.add(createIngredient(i));
    }
    return ingredients;
  }

  private Ingredient createIngredient(int i) {
    Ingredient ingredient = new Ingredient();
    ingredient.setIngredient("Random Ingredient");
    ingredient.setMeasure("cup");
    ingredient.setQuantity(i);
    return ingredient;
  }

  private List<Step> generateDummyStepsList() {
    List<Step> steps = new ArrayList<>();
    for (int i = 0; i < 11; i++) {
      steps.add(createStep(i));
    }
    return steps;
  }

  private Step createStep(int i) {
    Step step = new Step();
    step.setId(i);
    String randomDescription = String.valueOf(i+1) + ". " + UUID.randomUUID().toString();
    step.setDescription(randomDescription);
    step.setShortDescription(UUID.randomUUID().toString());
    step.setThumbnailURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdcf9_9-final-product-brownies/9-final-product-brownies.mp4");
    return step;
  }
}

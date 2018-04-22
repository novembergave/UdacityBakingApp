package com.novembergave.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.novembergave.bakingapp.pojo.Recipe;

public class RecipeActivity extends AppCompatActivity {

  private static final String CLASS = RecipeActivity.class.getName();
  private static final String EXTRA_RECIPE = CLASS + ".extra_recipe";
  private static final String TAG_PHONE_FRAGMENT = "tag_phone_fragment";

  public static Intent launchActivity(Context context, Recipe recipe) {
    Intent intent = new Intent(context, RecipeActivity.class);
    intent.putExtra(EXTRA_RECIPE, recipe);
    return intent;
  }

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
    Recipe recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
    setTitle(recipe.getName());
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    RecipePhoneFragment phoneFragment = RecipePhoneFragment.newInstance(recipe);
    getSupportFragmentManager().beginTransaction().replace(R.id.recipe_fragment_holder, phoneFragment, TAG_PHONE_FRAGMENT).commit();

  }

}

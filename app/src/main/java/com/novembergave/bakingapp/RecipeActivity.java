package com.novembergave.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.novembergave.bakingapp.recyclerviews.recipeactivity.RecipePhoneFragment;

public class RecipeActivity extends AppCompatActivity {

  private static final String CLASS = RecipeActivity.class.getName();
  private static final String EXTRA_NAME = CLASS + ".extra_name";
  private static final String TAG_PHONE_FRAGMENT = "tag_fragment_business_details";

  public static Intent launchActivity(Context context, String name) {
    Intent intent = new Intent(context, RecipeActivity.class);
    intent.putExtra(EXTRA_NAME, name);
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
    setTitle(getIntent().getStringExtra(EXTRA_NAME));
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    RecipePhoneFragment phoneFragment = RecipePhoneFragment.newInstance();
    getSupportFragmentManager().beginTransaction().replace(R.id.recipe_fragment_holder, phoneFragment, TAG_PHONE_FRAGMENT).commit();

  }

}

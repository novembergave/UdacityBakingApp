package com.novembergave.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewRecipeStepActivity extends AppCompatActivity {

  private static final String CLASS = ViewRecipeStepActivity.class.getName();

  private static final String EXTRA_URL = CLASS + ".extra_url";

  public static Intent launchActivity(Context context, String url) {
    Intent intent = new Intent(context, ViewRecipeStepActivity.class);
    intent.putExtra(EXTRA_URL, url);
    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_recipe_step);
  }
}

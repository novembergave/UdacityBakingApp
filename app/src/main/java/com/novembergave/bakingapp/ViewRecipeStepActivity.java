package com.novembergave.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.novembergave.bakingapp.pojo.Step;

public class ViewRecipeStepActivity extends AppCompatActivity {

  private static final String CLASS = ViewRecipeStepActivity.class.getName();
  private static final String EXTRA_NAME = CLASS + ".extra_name";
  private static final String EXTRA_STEP = CLASS + ".extra_step";
  private static final String TAG_PHONE_FRAGMENT = "tag_phone_fragment";

  public static Intent launchActivity(Context context, String name, Step step) {
    Intent intent = new Intent(context, ViewRecipeStepActivity.class);
    intent.putExtra(EXTRA_NAME, name);
    intent.putExtra(EXTRA_STEP, step);
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
    setContentView(R.layout.activity_view_recipe_step);
    setTitle(getIntent().getStringExtra(EXTRA_NAME));
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    ViewStepPhoneFragment phoneFragment = ViewStepPhoneFragment.newInstance(getIntent().getParcelableExtra(EXTRA_STEP));
    getSupportFragmentManager().beginTransaction().replace(R.id.view_step_fragment_holder, phoneFragment, TAG_PHONE_FRAGMENT).commit();
  }
}

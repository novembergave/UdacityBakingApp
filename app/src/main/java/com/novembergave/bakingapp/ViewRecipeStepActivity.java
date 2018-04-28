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
  private static final String STATE_FRAGMENT = CLASS + ".state_fragment";
  private ViewStepFragment phoneFragment;

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
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    getSupportFragmentManager().putFragment(outState, STATE_FRAGMENT, phoneFragment);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_recipe_step);
    setTitle(getIntent().getStringExtra(EXTRA_NAME));
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    if (savedInstanceState != null) {
      //Restore the fragment's instance
      phoneFragment = (ViewStepFragment) getSupportFragmentManager().getFragment(savedInstanceState, STATE_FRAGMENT);
    } else {
      phoneFragment = ViewStepFragment.newInstance(getIntent().getParcelableExtra(EXTRA_STEP));
      getSupportFragmentManager().beginTransaction().replace(R.id.view_step_fragment_holder, phoneFragment, TAG_PHONE_FRAGMENT).commit();
    }
  }
}

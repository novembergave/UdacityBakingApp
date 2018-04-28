package com.novembergave.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.novembergave.bakingapp.pojo.Recipe;

public class ViewRecipeStepActivity extends AppCompatActivity implements ViewStepFragment.OnNavigationSelected {

  private static final String CLASS = ViewRecipeStepActivity.class.getName();
  private static final String EXTRA_STEP_ID = CLASS + ".extra_step";
  private static final String EXTRA_RECIPE = CLASS + ".EXTRA_RECIPE";
  private static final String TAG_PHONE_FRAGMENT = "tag_phone_fragment";
  private static final String STATE_FRAGMENT = CLASS + ".state_fragment";
  private ViewStepFragment phoneFragment;
  private Recipe recipe;

  public static Intent launchActivity(Context context, Recipe recipe, long step) {
    Intent intent = new Intent(context, ViewRecipeStepActivity.class);
    intent.putExtra(EXTRA_RECIPE, recipe);
    intent.putExtra(EXTRA_STEP_ID, step);
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
    recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
    long stepId = getIntent().getLongExtra(EXTRA_STEP_ID, 0);

    setContentView(R.layout.activity_view_recipe_step);
    setTitle(recipe.getName());
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    if (savedInstanceState != null) {
      //Restore the fragment's instance
      phoneFragment = (ViewStepFragment) getSupportFragmentManager().getFragment(savedInstanceState, STATE_FRAGMENT);
    } else {
      phoneFragment = ViewStepFragment.newInstance(recipe.getSteps().get((int) stepId), recipe.getSteps().size());
      getSupportFragmentManager().beginTransaction().replace(R.id.view_step_fragment_holder, phoneFragment, TAG_PHONE_FRAGMENT).commit();
    }
  }

  @Override
  public void onNavigationSelected(long step) {
    phoneFragment = ViewStepFragment.newInstance(recipe.getSteps().get((int) step), recipe.getSteps().size());
    getSupportFragmentManager().beginTransaction().replace(R.id.view_step_fragment_holder, phoneFragment, TAG_PHONE_FRAGMENT).commit();
  }
}

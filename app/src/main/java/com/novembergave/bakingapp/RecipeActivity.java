package com.novembergave.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.novembergave.bakingapp.pojo.Recipe;
import com.novembergave.bakingapp.pojo.Step;

public class RecipeActivity extends AppCompatActivity implements RecipeStepsFragment.OnStepSelected, ViewStepFragment.OnNavigationSelected{

  private static final String CLASS = RecipeActivity.class.getName();
  private static final String EXTRA_RECIPE = CLASS + ".extra_recipe";
  private static final String TAG_PHONE_FRAGMENT = "tag_phone_fragment";
  private static final String TAG_TABLET_FRAGMENT = "tag_tablet_fragment";
  private static final String STATE_STEP = CLASS + ".state_step";

  private RecipeStepsFragment stepsFragment;
  private ViewStepFragment stepFragment;
  private Step step;
  private Recipe recipe;

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
    recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
    setTitle(recipe.getName());
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    if (savedInstanceState != null) {
      step = savedInstanceState.getParcelable(STATE_STEP);
    } else {
      step = recipe.getSteps().get(0); // show first step
    }

    stepsFragment = RecipeStepsFragment.newInstance(recipe);
    getSupportFragmentManager().beginTransaction().replace(R.id.recipe_fragment_steps_holder, stepsFragment, TAG_PHONE_FRAGMENT).commit();

    if (isTablet()) {
      stepFragment = ViewStepFragment.newInstance(step, recipe.getSteps().indexOf(step), recipe.getSteps().size());
      getSupportFragmentManager().beginTransaction().replace(R.id.recipe_fragment_step_holder, stepFragment, TAG_TABLET_FRAGMENT).commit();
    }
  }

  @Override
  public void onStepSelected(Step step) {
    if (isTablet()) {
      this.step = step;
      // Replace the fragment
      stepFragment = ViewStepFragment.newInstance(step, recipe.getSteps().indexOf(step), recipe.getSteps().size());
      getSupportFragmentManager().beginTransaction().replace(R.id.recipe_fragment_step_holder, stepFragment, TAG_TABLET_FRAGMENT).commit();
    }
  }

  private boolean isTablet() {
    return findViewById(R.id.recipe_fragment_step_holder) != null;
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (isTablet()) {
      outState.putParcelable(STATE_STEP, step);
    }
  }

  @Override
  public void onNavigationSelected(int stepPosition) {
    if (isTablet()) {
      this.step = recipe.getSteps().get(stepPosition);
      // Replace the fragment
      stepFragment = ViewStepFragment.newInstance(this.step, stepPosition, recipe.getSteps().size());
      getSupportFragmentManager().beginTransaction().replace(R.id.recipe_fragment_step_holder, stepFragment, TAG_TABLET_FRAGMENT).commit();
    }
  }
}

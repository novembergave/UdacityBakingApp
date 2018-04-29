package com.novembergave.bakingapp;

import android.app.Instrumentation;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.novembergave.bakingapp.pojo.Ingredient;
import com.novembergave.bakingapp.pojo.Recipe;
import com.novembergave.bakingapp.pojo.Step;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.Activity.RESULT_OK;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.novembergave.bakingapp.testingUtils.IntentMatchers.activityOf;
import static com.novembergave.bakingapp.testingUtils.RecipeFactory.generateDummyRecipe;
import static com.novembergave.bakingapp.testingUtils.RecyclerViewMatcher.withRecyclerView;
import static com.novembergave.bakingapp.utils.FormatUtils.isEmpty;
import static com.novembergave.bakingapp.utils.FormatUtils.removeStepNumber;
import static junit.framework.Assert.assertFalse;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {

  @Rule
  public ActivityTestRule<RecipeActivity> activityRule = new ActivityTestRule<>(RecipeActivity.class, true, false);

  @Before
  public void setUp() {
    Intents.init();
  }

  @After
  public void tearDown() {
    Intents.release();
  }

  @Test
  public void correctViewsAreVisibleDependingOnLayout() {
    // given
    Recipe recipe = generateDummyRecipe(1, 1);

    // perform - launch activity
    activityRule.launchActivity(RecipeActivity.launchActivity(getTargetContext(), recipe));

    // verify
    if (isTablet()) {
      // on tablet - both fragment holders are displayed
      onView(withId(R.id.recipe_fragment_steps_holder)).check(matches(isDisplayed()));
      onView(withId(R.id.recipe_fragment_step_holder)).check(matches(isDisplayed()));
    } else {
      // on phone - only one fragment holder is displayed
      onView(withId(R.id.recipe_fragment_steps_holder)).check(matches(isDisplayed()));
      onView(withId(R.id.recipe_fragment_step_holder)).check(doesNotExist());
    }
  }

  @Test
  public void recipeIngredientsAndStepsAreLoadedIntoRecyclerView() {
    // given
    int numberOfIngredients = 10;
    int numberOfSteps = 9;
    Recipe recipe = generateDummyRecipe(numberOfIngredients, numberOfSteps);

    // perform - launch activity
    activityRule.launchActivity(RecipeActivity.launchActivity(getTargetContext(), recipe));

    // verify - recyclerView is visible and contents are displayed
    onView(withId(R.id.ingredient_recycler_view)).check(matches(isDisplayed()));
    onView(withId(R.id.ingredient_recycler_view)).perform(scrollToPosition(0));
    onView(withRecyclerView(R.id.ingredient_recycler_view)
        .atPositionOnView(0, R.id.list_item_header))
        .check(matches(withText(R.string.ingredients)));
    for (int i = 0; i < numberOfIngredients; i++) {
      int actualPosition = i + 1;
      Ingredient ingredient = recipe.getIngredients().get(i);
      verifyIngredientIsDisplayed(actualPosition, ingredient);
    }

    onView(withRecyclerView(R.id.ingredient_recycler_view)
        .atPositionOnView(numberOfIngredients + 1, R.id.list_item_header))
        .check(matches(withText(R.string.steps)));

    for (int i = 0; i < numberOfSteps; i++) {
      int actualPosition = i + numberOfIngredients + 2;
      Step step = recipe.getSteps().get(i);
      verifyStepIsDisplayed(actualPosition, step);
    }
  }

  @Test
  public void clickingStepsOpensViewRecipeStepActivityWhenOnPhone() {
    // given
    int numberOfIngredients = 1;
    int numberOfSteps = 5;
    Recipe recipe = generateDummyRecipe(numberOfIngredients, numberOfSteps);
    intending(activityOf(ViewRecipeStepActivity.class))
        .respondWith(new Instrumentation.ActivityResult(RESULT_OK, null));

    // perform - launch activity
    activityRule.launchActivity(RecipeActivity.launchActivity(getTargetContext(), recipe));

    // verify - recyclerView is visible
    onView(withId(R.id.ingredient_recycler_view)).check(matches(isDisplayed()));

    // perform - scroll to and click one of the steps
    onView(withId(R.id.ingredient_recycler_view)).perform(scrollToPosition(4));
    onView(withRecyclerView(R.id.ingredient_recycler_view)
        .atPositionOnView(4, R.id.steps_root_view))
        .perform(click());

    // verify
    assertFalse(isTablet());
    intended(activityOf(ViewRecipeStepActivity.class));
  }

  private void verifyIngredientIsDisplayed(int actualPosition, Ingredient ingredient) {
    onView(withId(R.id.ingredient_recycler_view)).perform(scrollToPosition(actualPosition));
    onView(withRecyclerView(R.id.ingredient_recycler_view)
        .atPositionOnView(actualPosition, R.id.ingredient_quantity))
        .check(matches(withText(String.valueOf(ingredient.getQuantity()))));
    onView(withRecyclerView(R.id.ingredient_recycler_view)
        .atPositionOnView(actualPosition, R.id.ingredient_measure))
        .check(matches(withText(ingredient.getMeasure().toLowerCase())));
    onView(withRecyclerView(R.id.ingredient_recycler_view)
        .atPositionOnView(actualPosition, R.id.ingredient_name))
        .check(matches(withText(ingredient.getIngredient())));
  }

  private void verifyStepIsDisplayed(int actualPosition, Step step) {
    onView(withId(R.id.ingredient_recycler_view)).perform(scrollToPosition(actualPosition));
    onView(withRecyclerView(R.id.ingredient_recycler_view)
        .atPositionOnView(actualPosition, R.id.steps_number_label))
        .check(matches(withText(String.valueOf(step.getId()))));
    onView(withRecyclerView(R.id.ingredient_recycler_view)
        .atPositionOnView(actualPosition, R.id.steps_short_description))
        .check(matches(withText(step.getShortDescription())));
    onView(withRecyclerView(R.id.ingredient_recycler_view)
        .atPositionOnView(actualPosition, R.id.steps_long_description))
        .check(matches(withText(removeStepNumber(step.getDescription(), step.getId()))));
    onView(withRecyclerView(R.id.ingredient_recycler_view)
        .atPositionOnView(actualPosition, R.id.steps_video_icon))
        .check(!isEmpty(step.getVideoURL()) ? matches(isEnabled()) : matches(not(isEnabled())));
  }

  private boolean isTablet() {
    return getTargetContext().getResources().getBoolean(R.bool.isTablet);
  }

}

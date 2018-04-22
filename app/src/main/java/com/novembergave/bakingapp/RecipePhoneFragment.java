package com.novembergave.bakingapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novembergave.bakingapp.pojo.Ingredient;
import com.novembergave.bakingapp.pojo.Recipe;
import com.novembergave.bakingapp.pojo.Step;
import com.novembergave.bakingapp.recyclerviews.recipeactivity.IngredientsStepsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipePhoneFragment extends Fragment {

  private static final String CLASS = RecipePhoneFragment.class.getName();
  private static final String ARG_RECIPE = CLASS + ".arg_recipe";
  private Recipe recipe;

  public static RecipePhoneFragment newInstance(Recipe recipe) {
    RecipePhoneFragment fragment = new RecipePhoneFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(ARG_RECIPE, recipe);
    fragment.setArguments(bundle);
    return fragment;
  }
  private RecyclerView recyclerView;
  private IngredientsStepsAdapter adapter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_recipe, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    Bundle args = getArguments();
    recipe = args.getParcelable(ARG_RECIPE);
    recyclerView = view.findViewById(R.id.ingredient_recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    adapter = new IngredientsStepsAdapter(this::openActivity);
    recyclerView.setAdapter(adapter);
    adapter.setData(recipe.getIngredients(), recipe.getSteps());
  }


  private void openActivity(Step step) {
    startActivity(ViewRecipeStepActivity.launchActivity(getContext(), recipe.getName(), step));
  }

//  private List<Ingredient> generateDummyIngredientList() {
//    List<Ingredient> ingredients = new ArrayList<>();
//    for (int i = 0; i < 10; i++) {
//      ingredients.add(createIngredient(i));
//    }
//    return ingredients;
//  }
//
//  private Ingredient createIngredient(int i) {
//    Ingredient ingredient = new Ingredient();
//    ingredient.setIngredient("Random Ingredient");
//    ingredient.setMeasure("cup");
//    ingredient.setQuantity(i);
//    return ingredient;
//  }
//
//  private List<Step> generateDummyStepsList() {
//    List<Step> steps = new ArrayList<>();
//    for (int i = 0; i < 11; i++) {
//      steps.add(createStep(i));
//    }
//    return steps;
//  }
//
//  private Step createStep(int i) {
//    Step step = new Step();
//    step.setId(i);
//    String randomDescription = String.valueOf(i+1) + ". " + UUID.randomUUID().toString();
//    step.setDescription(randomDescription);
//    step.setShortDescription(UUID.randomUUID().toString());
//    step.setThumbnailURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffdcf9_9-final-product-brownies/9-final-product-brownies.mp4");
//    return step;
//  }
}

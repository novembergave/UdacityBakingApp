package com.novembergave.bakingapp.recyclerviews.recipeactivity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novembergave.bakingapp.R;
import com.novembergave.bakingapp.ViewRecipeStepActivity;
import com.novembergave.bakingapp.pojo.Ingredient;
import com.novembergave.bakingapp.pojo.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RecipePhoneFragment extends Fragment {

  public static RecipePhoneFragment newInstance() {
    return new RecipePhoneFragment();
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
    recyclerView = view.findViewById(R.id.ingredient_recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    adapter = new IngredientsStepsAdapter(this::openActivity);
    recyclerView.setAdapter(adapter);
    adapter.setData(generateDummyIngredientList(), generateDummyStepsList());
  }


  private void openActivity(String url) {
    startActivity(ViewRecipeStepActivity.launchActivity(getContext(), url));
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

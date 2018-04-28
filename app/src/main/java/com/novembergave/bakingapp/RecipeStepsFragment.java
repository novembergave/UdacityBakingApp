package com.novembergave.bakingapp;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.novembergave.bakingapp.pojo.Recipe;
import com.novembergave.bakingapp.pojo.Step;
import com.novembergave.bakingapp.recyclerviews.recipeactivity.IngredientsStepsAdapter;

public class RecipeStepsFragment extends Fragment {

  private static final String CLASS = RecipeStepsFragment.class.getName();
  private static final String ARG_RECIPE = CLASS + ".arg_recipe";
  private Recipe recipe;

  public static RecipeStepsFragment newInstance(Recipe recipe) {
    RecipeStepsFragment fragment = new RecipeStepsFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(ARG_RECIPE, recipe);
    fragment.setArguments(bundle);
    return fragment;
  }

  public interface OnStepSelected {
    void onStepSelected(Step step);
  }

  private RecyclerView recyclerView;
  private IngredientsStepsAdapter adapter;
  private OnStepSelected callback;

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

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    // This makes sure that the container activity has implemented
    // the callback interface. If not, it throws an exception
    try {
      callback = (OnStepSelected) context;
    } catch (ClassCastException e) {
      throw new ClassCastException(context.toString()
          + " must implement OnStepSelected");
    }
  }

  private void openActivity(Step step) {
    // If this is a tablet, we want to open the detail in the fragment next to it
    if (getResources().getBoolean(R.bool.isTablet)) {
      callback.onStepSelected(step);
    } else {
      startActivity(ViewRecipeStepActivity.launchActivity(getContext(), recipe, step.getId()));
    }
  }
}

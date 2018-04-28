package com.novembergave.bakingapp.recyclerviews.mainactivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novembergave.bakingapp.R;
import com.novembergave.bakingapp.pojo.Recipe;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

  private final View cardView;
  private final TextView recipeName;

  public static RecipeViewHolder inflateItemViewFrom(ViewGroup viewGroup) {
    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_recipe, viewGroup, false);
    return new RecipeViewHolder(v);
  }

  public RecipeViewHolder(View itemView) {
    super(itemView);
    cardView = itemView.findViewById(R.id.recipe_card);
    recipeName = itemView.findViewById(R.id.recipe_name);
  }

  public void bindTo(Recipe recipe, final MainAdapter.RecipeClickListener listener) {
    recipeName.setText(recipe.getName());
    cardView.setOnClickListener(click -> listener.viewRecipe(recipe));
  }
}

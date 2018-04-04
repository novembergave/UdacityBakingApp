package com.novembergave.bakingapp.recyclerviews.mainactivity;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novembergave.bakingapp.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

  private final TextView recipeName;
  private final Context context;

  public static RecipeViewHolder inflateItemViewFrom(ViewGroup viewGroup) {
    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_recipe, viewGroup, false);
    return new RecipeViewHolder(v);
  }

  public RecipeViewHolder(View itemView) {
    super(itemView);
    context = itemView.getContext();
    recipeName = itemView.findViewById(R.id.recipe_name);
  }

  public void bindTo(int position, final MainAdapter.RecipeClickListener listener) {
    recipeName.setText(String.valueOf(position));
    recipeName.setOnClickListener(click -> listener.viewRecipe(position));
  }
}

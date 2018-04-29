package com.novembergave.bakingapp.recyclerviews.mainactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.novembergave.bakingapp.R;
import com.novembergave.bakingapp.pojo.Recipe;
import com.novembergave.bakingapp.utils.FormatUtils;
import com.squareup.picasso.Picasso;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

  private final Context context;
  private final View cardView;
  private final ImageView imageView;
  private final TextView recipeName;
  private final TextView servesNumber;

  public static RecipeViewHolder inflateItemViewFrom(ViewGroup viewGroup) {
    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_recipe, viewGroup, false);
    return new RecipeViewHolder(v);
  }

  public RecipeViewHolder(View itemView) {
    super(itemView);
    context = itemView.getContext();
    cardView = itemView.findViewById(R.id.recipe_card);
    imageView = itemView.findViewById(R.id.recipe_image);
    recipeName = itemView.findViewById(R.id.recipe_name);
    servesNumber = itemView.findViewById(R.id.recipe_serves_text);
  }

  public void bindTo(Recipe recipe, final MainAdapter.RecipeClickListener listener) {
    // if an image exists, load it here
    if (!FormatUtils.isEmpty(recipe.getImage())) {
      Picasso.with(context).load(recipe.getImage()).into(imageView);
    }
    recipeName.setText(recipe.getName());
    servesNumber.setText(String.valueOf(recipe.getServings()));
    cardView.setOnClickListener(click -> listener.viewRecipe(recipe));
  }
}

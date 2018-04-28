package com.novembergave.bakingapp.recyclerviews.recipeactivity;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novembergave.bakingapp.R;
import com.novembergave.bakingapp.pojo.Ingredient;

public class IngredientsViewHolder extends RecyclerView.ViewHolder {

  private final TextView quantity;
  private final TextView measure;
  private final TextView ingredientName;

  public static IngredientsViewHolder inflateItemViewFrom(ViewGroup viewGroup) {
    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_ingredient, viewGroup, false);
    return new IngredientsViewHolder(v);
  }

  public IngredientsViewHolder(View itemView) {
    super(itemView);
    quantity = itemView.findViewById(R.id.ingredient_quantity);
    measure = itemView.findViewById(R.id.ingredient_measure);
    ingredientName = itemView.findViewById(R.id.ingredient_name);

  }

  public void bindTo(Ingredient ingredient) {
    quantity.setText(String.valueOf(ingredient.getQuantity()));
    measure.setText(ingredient.getMeasure().toLowerCase());
    ingredientName.setText(ingredient.getIngredient());
  }
}
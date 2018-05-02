package com.novembergave.bakingapp.widget;


import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.novembergave.bakingapp.R;
import com.novembergave.bakingapp.pojo.Ingredient;
import com.novembergave.bakingapp.utils.SharedPreferencesUtils;

import java.util.List;

class ListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

  private Context context;

  private List<Ingredient> ingredients;


  public ListViewRemoteViewsFactory(Context context, Intent intent) {
    this.context = context;
  }

  @Override
  public void onCreate() {
    ingredients = SharedPreferencesUtils.getSelectedRecipe(context).getIngredients();
  }

  @Override
  public RemoteViews getViewAt(int position) {
    RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.list_item_ingredient_widget);

    Ingredient ingredient = ingredients.get(position);
    rv.setTextViewText(R.id.ingredient_quantity, String.valueOf(ingredient.getQuantity()));
    rv.setTextViewText(R.id.ingredient_measure, ingredient.getMeasure().toLowerCase());
    rv.setTextViewText(R.id.ingredient_name, ingredient.getIngredient());
    return rv;

  }

  @Override
  public int getCount() {
    return ingredients.size();
  }

  @Override
  public void onDataSetChanged() {
    ingredients = SharedPreferencesUtils.getSelectedRecipe(context).getIngredients();
  }

  @Override
  public int getViewTypeCount() {
    return 1;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public void onDestroy() {
    ingredients.clear();
  }

  @Override
  public boolean hasStableIds() {
    return true;
  }

  @Override
  public RemoteViews getLoadingView() {
    return null;
  }
}
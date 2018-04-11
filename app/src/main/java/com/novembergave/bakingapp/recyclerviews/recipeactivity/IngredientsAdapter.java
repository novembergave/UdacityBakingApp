package com.novembergave.bakingapp.recyclerviews.recipeactivity;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.novembergave.bakingapp.pojo.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {

  private List<Ingredient> list;

  public IngredientsAdapter() {
    this.list = new ArrayList<>();
  }

  @Override
  public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return IngredientsViewHolder.inflateItemViewFrom(parent);
  }

  @Override
  public void onBindViewHolder(IngredientsViewHolder holder, int position) {
    holder.bindTo(list.get(position));
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public void setData(List<Ingredient> list) {
    this.list = list;
    notifyDataSetChanged();
  }
}

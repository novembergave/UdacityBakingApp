package com.novembergave.bakingapp.recyclerviews.mainactivity;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.novembergave.bakingapp.pojo.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

  public interface RecipeClickListener {
    void viewRecipe(Recipe recipe);
  }

  private RecipeClickListener listener;
  private List<Recipe> list;


  public MainAdapter(RecipeClickListener listener) {
    this.listener = listener;
    this.list = new ArrayList<>();
  }

  @Override
  public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return RecipeViewHolder.inflateItemViewFrom(parent);
  }

  @Override
  public void onBindViewHolder(RecipeViewHolder holder, int position) {
    holder.bindTo(list.get(position), listener);
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public void setData(List<Recipe> list) {
    this.list = list;
    notifyDataSetChanged();
  }

}

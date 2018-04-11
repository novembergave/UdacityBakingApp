package com.novembergave.bakingapp.recyclerviews.recipeactivity;


import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.novembergave.bakingapp.pojo.Step;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsViewHolder> {

  public interface StepsClickListener {
    void viewVideo(String url);
  }

  private StepsClickListener listener;
  private List<Step> list;


  public StepsAdapter(StepsClickListener listener) {
    this.listener = listener;
    this.list = new ArrayList<>();
  }

  @Override
  public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return StepsViewHolder.inflateItemViewFrom(parent);
  }

  @Override
  public void onBindViewHolder(StepsViewHolder holder, int position) {
    holder.bindTo(list.get(position), listener);
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public void setData(List<Step> steps) {
    list = steps;
    notifyDataSetChanged();
  }

}

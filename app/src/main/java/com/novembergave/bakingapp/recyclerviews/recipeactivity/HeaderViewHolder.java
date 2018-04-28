package com.novembergave.bakingapp.recyclerviews.recipeactivity;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novembergave.bakingapp.R;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

  private final Context context;
  private final TextView header;

  public static HeaderViewHolder inflateItemViewFrom(ViewGroup viewGroup) {
    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_header, viewGroup, false);
    return new HeaderViewHolder(v);
  }

  public HeaderViewHolder(View itemView) {
    super(itemView);
    context = itemView.getContext();
    header = itemView.findViewById(R.id.list_item_header);
  }

  public void bindTo(boolean isIngredientHeader, boolean isStepHeader) {
    if (isIngredientHeader) {
      header.setText(context.getString(R.string.ingredients));
      return;
    }
    if (isStepHeader) {
      header.setText(context.getString(R.string.steps));
    }
  }
}

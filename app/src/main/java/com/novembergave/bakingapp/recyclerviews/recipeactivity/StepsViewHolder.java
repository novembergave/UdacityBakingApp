package com.novembergave.bakingapp.recyclerviews.recipeactivity;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.novembergave.bakingapp.R;
import com.novembergave.bakingapp.pojo.Step;

import static com.novembergave.bakingapp.utils.FormatUtils.removeStepNumber;

public class StepsViewHolder extends RecyclerView.ViewHolder {

  private final View rootView;
  private final TextView stepsNumber;
  private final TextView shortDescription;
  private final TextView longDescription;
  private final ImageView videoIcon;

  public static StepsViewHolder inflateItemViewFrom(ViewGroup viewGroup) {
    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_steps, viewGroup, false);
    return new StepsViewHolder(v);
  }

  public StepsViewHolder(View itemView) {
    super(itemView);
    rootView = itemView.findViewById(R.id.steps_root_view);
    stepsNumber = itemView.findViewById(R.id.steps_number_label);
    shortDescription = itemView.findViewById(R.id.steps_short_description);
    longDescription = itemView.findViewById(R.id.steps_long_description);
    videoIcon = itemView.findViewById(R.id.steps_video_icon);
  }

  public void bindTo(Step step, StepsAdapter.StepsClickListener listener) {
    stepsNumber.setText(String.valueOf(step.getId()));
    shortDescription.setText(step.getShortDescription());
    String formattedDescription = removeStepNumber(step.getDescription(), step.getId());
    longDescription.setText(formattedDescription);
    // If there is some link to video, display video icon and enable onClick
    videoIcon.setVisibility(step.getThumbnailURL() != null || step.getVideoURL() != null ? View.VISIBLE : View.GONE);
    if (step.getThumbnailURL() != null) {
      rootView.setOnClickListener(click -> listener.viewVideo(step.getThumbnailURL()));
    } else if (step.getVideoURL() != null) {
      rootView.setOnClickListener(click -> listener.viewVideo(step.getVideoURL()));
    }
  }
}

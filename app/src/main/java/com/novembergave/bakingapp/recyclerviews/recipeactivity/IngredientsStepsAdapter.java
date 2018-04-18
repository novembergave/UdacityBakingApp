package com.novembergave.bakingapp.recyclerviews.recipeactivity;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.novembergave.bakingapp.pojo.Ingredient;
import com.novembergave.bakingapp.pojo.Step;

import java.util.ArrayList;
import java.util.List;

public class IngredientsStepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  public interface StepsClickListener {
    void viewVideo(Step step);
  }

  private final int VIEW_TYPE_HEADER = 100;
  private final int VIEW_TYPE_INGREDIENT = 101;
  private final int VIEW_TYPE_STEP = 102;


  private List<IngredientStepFeedModel> models;
  private StepsClickListener listener;

  public IngredientsStepsAdapter(StepsClickListener listener) {
    this.listener = listener;
    this.models = new ArrayList<>();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case VIEW_TYPE_HEADER:
        return HeaderViewHolder.inflateItemViewFrom(parent);
      case VIEW_TYPE_INGREDIENT:
        return IngredientsViewHolder.inflateItemViewFrom(parent);
      case VIEW_TYPE_STEP:
        return StepsViewHolder.inflateItemViewFrom(parent);
      default:
        throw new RuntimeException("onCreateViewHolder: unknown view type");
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    IngredientStepFeedModel model = models.get(position);
    // Headers
    if (model.header != null) {
      ((HeaderViewHolder) holder).bindTo(model.header.isIngredientHeader, model.header.isStepHeader);
    }
    // Listings
    else if (model.ingredient != null) {
      ((IngredientsViewHolder) holder).bindTo(model.ingredient);
    } else if (model.step != null) {
      ((StepsViewHolder) holder).bindTo(model.step, listener);
    }
  }

  @Override
  public int getItemCount() {
    return models.size();
  }

  @Override
  public int getItemViewType(int position) {
    if (models.get(position).header != null) {
      return VIEW_TYPE_HEADER;
    } else if (models.get(position).ingredient != null) {
      return VIEW_TYPE_INGREDIENT;
    } else {
      return VIEW_TYPE_STEP;
    }
  }

  public void setData(List<Ingredient> ingredients, List<Step> steps) {
    models.clear(); // clear the list before re-setting
    models.add(new IngredientStepFeedModel(null, null, new Header(true, false)));
    for (Ingredient ingredient : ingredients) {
      models.add(new IngredientStepFeedModel(ingredient, null, null));
    }
    models.add(new IngredientStepFeedModel(null, null, new Header(false, true)));
    for (Step step : steps) {
      models.add(new IngredientStepFeedModel(null, step, null));
    }
    notifyDataSetChanged();
  }

  private class Header {
    boolean isIngredientHeader;
    boolean isStepHeader;

    Header(boolean isIngredientHeader, boolean isStepHeader) {
      this.isIngredientHeader = isIngredientHeader;
      this.isStepHeader = isStepHeader;
    }
  }
  private class IngredientStepFeedModel {
    Ingredient ingredient;
    Step step;
    Header header;

    public IngredientStepFeedModel(Ingredient ingredient, Step step, Header header) {
      this.ingredient = ingredient;
      this.step = step;
      this.header = header;
    }
  }
}

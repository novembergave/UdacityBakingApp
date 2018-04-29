package com.novembergave.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.novembergave.bakingapp.R;
import com.novembergave.bakingapp.pojo.Recipe;
import com.novembergave.bakingapp.utils.SharedPreferencesUtils;

public class IngredientsWidget extends AppWidgetProvider {

  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                              int appWidgetId) {

    // Retrieved SharedPref
    Recipe selectedRecipe = SharedPreferencesUtils.getSelectedRecipe(context);

    // Construct the RemoteViews object
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
    if (selectedRecipe != null) {
      views.setTextViewText(R.id.appwidget_title, selectedRecipe.getName());
      views.setTextViewText(R.id.appwidget_ingredients_title, context.getString(R.string.ingredients));
    } else {
      views.setTextViewText(R.id.appwidget_title, context.getString(R.string.app_name));
      views.setTextViewText(R.id.appwidget_ingredients_title, context.getString(R.string.widget_load_to_show));
    }

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views);
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    // There may be multiple widgets active, so update all of them
    for (int appWidgetId : appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId);
    }
  }

  @Override
  public void onEnabled(Context context) {
    // Enter relevant functionality for when the first widget is created
  }

  @Override
  public void onDisabled(Context context) {
    // Enter relevant functionality for when the last widget is disabled
  }
}


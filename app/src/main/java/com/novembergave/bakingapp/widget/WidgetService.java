package com.novembergave.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.novembergave.bakingapp.R;

public class WidgetService extends IntentService {

  private static final String CLASS = WidgetService.class.getName();
  public static final String ACTION_UPDATE_RECIPE = CLASS + ".action_update_recipe";

  public WidgetService() {
    super(CLASS);
  }

  /**
   * Starts this service to perform handleUpdateRecipe action with the given parameters. If
   * the service is already performing a task this action will be queued.
   *
   * @see IntentService
   */
  public static void updateWidget(Context context) {
    Intent intent = new Intent(context, WidgetService.class);
    intent.setAction(ACTION_UPDATE_RECIPE);
    context.startService(intent);
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {
    if (intent != null) {
        handleUpdateRecipe();
    }
  }

  private void handleUpdateRecipe() {
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));
    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_ingredients_list);
    //Now update all widgets
    for (int appWidgetId : appWidgetIds) {
      IngredientsWidgetProvider.updateAppWidget(this, appWidgetManager, appWidgetId);
    }
  }
}
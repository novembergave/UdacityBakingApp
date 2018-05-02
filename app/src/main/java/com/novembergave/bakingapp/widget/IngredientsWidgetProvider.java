package com.novembergave.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.novembergave.bakingapp.MainActivity;
import com.novembergave.bakingapp.R;
import com.novembergave.bakingapp.pojo.Recipe;
import com.novembergave.bakingapp.utils.SharedPreferencesUtils;

public class IngredientsWidgetProvider extends AppWidgetProvider {

  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                              int appWidgetId) {

    // Retrieved SharedPref
    Recipe selectedRecipe = SharedPreferencesUtils.getSelectedRecipe(context);

    // Construct the RemoteViews object
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
    if (selectedRecipe != null) {
      views.setTextViewText(R.id.appwidget_title, selectedRecipe.getName());
      views.setTextViewText(R.id.appwidget_ingredients_title, context.getString(R.string.ingredients));
      Intent intent = new Intent(context, ListViewWidgetService.class);
      views.setRemoteAdapter(R.id.appwidget_ingredients_list, intent);
      views.setEmptyView(R.id.appwidget_ingredients_list, R.id.appwidget_ingredients_empty_view);
      setOpenAppIntent(context, views, MainActivity.launchRecipeFromWidget(context, selectedRecipe));
    } else {
      views.setTextViewText(R.id.appwidget_title, context.getString(R.string.app_name));
      views.setTextViewText(R.id.appwidget_ingredients_title, context.getString(R.string.widget_load_to_show));
      setOpenAppIntent(context, views, new Intent(context, MainActivity.class));
    }

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views);
  }

  private static void setOpenAppIntent(Context context, RemoteViews views, Intent intent) {
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
    // Get the layout for the App Widget and attach an on-click listener to it
    views.setOnClickPendingIntent(R.id.appwidget_title, pendingIntent);
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

  public static void sendRefreshBroadcast(Context context) {
    Intent intent = new Intent(context, IngredientsWidgetProvider.class);
    intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
    int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(
        new ComponentName(context, IngredientsWidgetProvider.class));
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
    context.sendBroadcast(intent);
  }

  @Override
  public void onReceive(final Context context, Intent intent) {
    final String action = intent.getAction();
    if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
      // refresh all your widgets
      AppWidgetManager manager = AppWidgetManager.getInstance(context);
      ComponentName componentName = new ComponentName(context, IngredientsWidgetProvider.class);
      manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(componentName), R.id.appwidget_ingredients_list);
    }
    super.onReceive(context, intent);
  }
}


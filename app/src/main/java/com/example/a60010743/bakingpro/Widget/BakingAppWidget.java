package com.example.a60010743.bakingpro.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.a60010743.bakingpro.R;
import com.example.a60010743.bakingpro.model.RecepieIngredients;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    private static final String mSharedPrefFile = "com.example.a60010743.bakingpro";
    private static final String COUNT_KEY = "count";
    static List<RecepieIngredients> mIngredients;
    static String mRecepieName;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Log.d(TAG, "Inside UPDATEWIDGET"+mRecepieName);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.widgetTitleLabel, mRecepieName);
        Intent intent = new Intent(context, BakingWidgetRemoteViewService.class);
        views.setRemoteAdapter(R.id.widgetListView, intent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            //updateAppWidget(context, appWidgetManager, appWidgetId);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
            Intent intent = new Intent(context, BakingWidgetRemoteViewService.class);
            views.setRemoteAdapter(R.id.widgetListView, intent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    public static void onUpdateWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds,
                                      String recepieName, List<RecepieIngredients> recepieIngredients) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
           // updateAppWidget(context, appWidgetManager, appWidgetId, recepieName, recepieIngredients);
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

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Inside OnReceive");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakingAppWidget.class));
        Log.d(TAG, "onReceive: app widgetid len "+appWidgetIds.length);

        if(intent.getAction() == AppWidgetManager.ACTION_APPWIDGET_UPDATE){
            Log.d(TAG, "Inside OnReceive-IF");
            mIngredients = intent.getParcelableArrayListExtra("ingredients");
            mRecepieName = intent.getStringExtra("recepieName");
            Log.d(TAG, "onReceive: ingredients recieved? "+(mIngredients!=null));
            if(mIngredients!=null) {
                Log.d(TAG, "onReceive: ingredients "+mIngredients.size());
                for (int appWidgetId : appWidgetIds) {
                    Log.d(TAG, "onReceive: ing is full app widgetid "+appWidgetId);
                    updateAppWidget(context, appWidgetManager, appWidgetId);
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widgetListView);
                }
            }
        }
        super.onReceive(context, intent);
    }
}


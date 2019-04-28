package com.example.a60010743.bakingpro.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.a60010743.bakingpro.R;
import com.example.a60010743.bakingpro.Utilities.JsonParseUtils;
import com.example.a60010743.bakingpro.model.RecepieDao;
import com.example.a60010743.bakingpro.model.RecepieDetails;
import com.example.a60010743.bakingpro.model.RecepieDetailsDatabase;
import com.example.a60010743.bakingpro.model.RecepieIngredients;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class UpdateWidgetService extends IntentService {

    private static final String ACTION_UPDATE_WIDGET =
                                "com.example.a60010743.bakingpro.action.update_widget";
    private static final String ACTION_DELETE_UPDATE_WIDGET =
            "com.example.a60010743.bakingpro.action.remove_data_widget";
    private static ArrayList<RecepieIngredients> mIngredients;
    private static String mIngData;
    private static String mRecepieName;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public UpdateWidgetService() {
        super("UpdateWidgetService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void startAddWidgetData(Context context, List<RecepieIngredients> recepieIngredients,
                                          String recepieName) {
        try {
            Log.d(TAG, "Inside StartAddWidget");
            Intent intent = new Intent(context, UpdateWidgetService.class);
            intent.setAction(ACTION_UPDATE_WIDGET);
            for(RecepieIngredients rec:recepieIngredients) {
                Log.d("TESTTTTT", rec.getIngredients());
                Log.d("TESTTTTT", rec.getMeasure());
                Log.d("TESTTTTT", rec.getQuantity());
            }
            intent.putParcelableArrayListExtra("ingredients", (ArrayList<? extends Parcelable>) recepieIngredients);
            intent.putExtra("recepieName", recepieName);
            Log.d(TAG, "Inside StartAddWidget Going to start service");
            context.startService(intent);
        } catch (Exception e) {
            Log.d(TAG, "Inside StartAddWidget EXception");
            Log.e(TAG, "Exception starting service", e);
        }
    }

    public static void startDeleteWidgetData(Context context, List<RecepieIngredients> recepieIngredients) {
        Intent intent = new Intent(context, UpdateWidgetService.class);
        intent.setAction(ACTION_DELETE_UPDATE_WIDGET);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Inside OnHandleIntent");
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGET.equals(action)) {
                Log.d(TAG, "Inside OnHandleIntent-IF");
                mIngredients = intent.getParcelableArrayListExtra("ingredients");
                mRecepieName = intent.getStringExtra("recepieName");
                handleUpdateWidget(mIngredients, mRecepieName);
            }
        }
    }

    private void handleUpdateWidget(List<RecepieIngredients> recepieIngredients, String recepieName) {
        if(recepieIngredients != null) {

//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
//            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));
//            BakingAppWidget.onUpdateWidget(this, appWidgetManager, appWidgetIds, recepieName,
//                                            recepieIngredients);
//            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetListView);
            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putParcelableArrayListExtra("ingredients", (ArrayList<? extends Parcelable>) recepieIngredients);
            intent.putExtra("recepieName", recepieName);
            sendBroadcast(intent);
        }
    }
}

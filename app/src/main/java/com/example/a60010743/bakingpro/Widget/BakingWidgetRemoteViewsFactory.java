package com.example.a60010743.bakingpro.Widget;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.example.a60010743.bakingpro.R;
import com.example.a60010743.bakingpro.model.RecepieDao;
import com.example.a60010743.bakingpro.model.RecepieDetails;
import com.example.a60010743.bakingpro.model.RecepieDetailsDatabase;
import com.example.a60010743.bakingpro.model.RecepieIngredients;
import com.example.a60010743.bakingpro.model.RecepieRepository;
import com.example.a60010743.bakingpro.model.RecepieViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class BakingWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;
    private RecepieViewModel mRecepieViewModel;
    private List<String> mFavRecepieDetails = new ArrayList<String>();
    private List<RecepieIngredients> mIngredients;
    private String mRecepieName;
    public BakingWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;

    }

    @Override
    public void onCreate() {



    }

    @Override
    public void onDataSetChanged() {
        mIngredients = BakingAppWidget.mIngredients;
        mRecepieName = BakingAppWidget.mRecepieName;

        final long identityToken = Binder.clearCallingIdentity();
//        mFavRecepieDetails.clear();
//        mFavRecepieDetails.add("Test1");
//        mFavRecepieDetails.add("Test2");
//        mFavRecepieDetails.add("Test3");
//        for(String res:mFavRecepieDetails){
//            Log.d("WidgetTEXT-", res);
//        }
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mIngredients == null) return 0;
        return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if(mIngredients == null) {
            return null;
        }
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.collection_widget_list_item);
        //rv.setTextViewText(R.id.widgetItemTaskNameLabel, mFavRecepieDetails.get(0));
        String ing = mIngredients.get(position).getIngredients()+" "+ mIngredients.get(position).getQuantity()+
                                    " "+ mIngredients.get(position).getMeasure();
        Log.d("INGDATA", ing);
        rv.setTextViewText(R.id.widgetItemTaskNameLabel, ing);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

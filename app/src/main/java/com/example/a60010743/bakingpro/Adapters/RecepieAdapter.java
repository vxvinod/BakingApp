package com.example.a60010743.bakingpro.Adapters;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a60010743.bakingpro.MainActivity;
import com.example.a60010743.bakingpro.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecepieAdapter extends BaseAdapter {
    private Context mContext;
    //private List<String> mRecepieNames;
    private List<String> mRecepieNames;

    public RecepieAdapter(Context context, List<String> recepieNames) {
        mContext = context;
        mRecepieNames = recepieNames;
        //final List<String> mRecepieNames = new ArrayList<String>(Arrays.asList(recepieNames));
    }

    @Override
    public int getCount() {
        if(mRecepieNames == null) { return 0; }
        return mRecepieNames.size();
    }

    public void setRecepieItems(List<String> recepieItems) {
        mRecepieNames = recepieItems;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View gridView;
        LayoutInflater inflater = (LayoutInflater) mContext
                                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            gridView = new View(mContext);
            gridView = inflater.inflate(R.layout.grid_view_layout, null);
            TextView tv = (TextView) gridView.findViewById(R.id.android_gridview_text);
            tv.setText(mRecepieNames.get(position));
        } else {
            gridView = (View) convertView;
        }
        return gridView;
    }
//    // Method for converting DP value to pixels
//    public static int getPixelsFromDPs(Activity activity, int dps){
//        Resources r = activity.getResources();
//        int  px = (int) (TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
//        return px;
//    }
}

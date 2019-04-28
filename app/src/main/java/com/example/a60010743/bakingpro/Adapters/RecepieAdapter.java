package com.example.a60010743.bakingpro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a60010743.bakingpro.R;

import java.util.List;

public class RecepieAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mRecepieNames;

    public RecepieAdapter(Context context, List<String> recepieNames) {
        mContext = context;
        mRecepieNames = recepieNames;
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
            gridView = inflater.inflate(R.layout.grid_view_layout, null);
            TextView tv = (TextView) gridView.findViewById(R.id.recepie_item_gridview);
            tv.setText(mRecepieNames.get(position));
        } else {
            gridView = (View) convertView;
        }
        return gridView;
    }
}

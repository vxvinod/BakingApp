package com.example.a60010743.bakingpro.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a60010743.bakingpro.R;

import org.w3c.dom.Text;

public class RecepieStepsAdapter extends RecyclerView.Adapter<RecepieStepsAdapter.MyViewHolder>{

    private Context mContext;
    private String[] mRecepieSteps;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.recepie_recycler_view_text) ;
        }

    }

    public RecepieStepsAdapter(Context context, String[] recepieSteps) {
        mRecepieSteps = recepieSteps;
    }
    @NonNull
    @Override
    public RecepieStepsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View tv = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.rec_step_layout_text_view, viewGroup, false);
        MyViewHolder vh = new MyViewHolder(tv);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(mRecepieSteps[i]);
    }

    @Override
    public int getItemCount() {
        return mRecepieSteps.length;
    }


}

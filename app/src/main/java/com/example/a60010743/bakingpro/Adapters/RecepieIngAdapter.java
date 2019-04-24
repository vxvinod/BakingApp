package com.example.a60010743.bakingpro.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a60010743.bakingpro.R;
import com.example.a60010743.bakingpro.RecepieDetailsActivity;
import com.example.a60010743.bakingpro.model.RecepieIngredients;

import java.util.List;


public class RecepieIngAdapter extends RecyclerView.Adapter<RecepieIngAdapter.MyViewHolder>{

    private Context mContext;
    private List<RecepieIngredients> mRecepieIng;
    private String mRecepieItem;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.recepie_ing_tv) ;
        }



    }

    public RecepieIngAdapter(Context context, List<RecepieIngredients> recepieIng) {
        mContext = context;
        mRecepieIng = recepieIng;
    }

    public void setRecepieSteps(List<RecepieIngredients> recepieStepDetails) {
        mRecepieIng = recepieStepDetails;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecepieIngAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View tv = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.rec_ing_layout, viewGroup, false);
        RecepieIngAdapter.MyViewHolder vh = new RecepieIngAdapter.MyViewHolder(tv);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecepieIngAdapter.MyViewHolder myViewHolder, final int i) {
        String ing = mRecepieIng.get(i).getIngredients() + ": "+ mRecepieIng.get(i).getQuantity() +
                        " " + mRecepieIng.get(i).getMeasure();
        myViewHolder.textView.setText(ing);
        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecepieDetailsActivity.class);
                //intent.putExtra("recStpDetails",  mRecepieSteps.get(i));
                intent.putExtra("recepieItem",  mRecepieItem);
                intent.putExtra("navigationIndex", i);
                mContext.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mRecepieIng.size();
    }


}

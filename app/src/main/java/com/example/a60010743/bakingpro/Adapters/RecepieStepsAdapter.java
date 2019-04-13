package com.example.a60010743.bakingpro.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.a60010743.bakingpro.Fragments.RecepieStepsFragments;
import com.example.a60010743.bakingpro.R;
import com.example.a60010743.bakingpro.RecepieDetailsActivity;
import com.example.a60010743.bakingpro.RecepieStepsActivity;
import com.example.a60010743.bakingpro.model.RecepieStepDetails;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

public class RecepieStepsAdapter extends RecyclerView.Adapter<RecepieStepsAdapter.MyViewHolder>{

    private Context mContext;
    private List<RecepieStepDetails> mRecepieSteps;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.recepie_recycler_view_text) ;
        }



    }

    public RecepieStepsAdapter(Context context, List<RecepieStepDetails> recepieSteps) {
        mContext = context;
        mRecepieSteps = recepieSteps;
    }

    public void setRecepieSteps(List<RecepieStepDetails> recepieStepDetails) {
        mRecepieSteps = recepieStepDetails;
        notifyDataSetChanged();
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
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.textView.setText(mRecepieSteps.get(i).getShortDesc());
        myViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecepieDetailsActivity.class);
                intent.putExtra("recStpDetails",  mRecepieSteps.get(i));
                mContext.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mRecepieSteps.size();
    }


}

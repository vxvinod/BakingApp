package com.example.a60010743.bakingpro.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.List;

public class RecepieStepsAdapter extends RecyclerView.Adapter<RecepieStepsAdapter.MyViewHolder>{
    RecepieStepsFragments.OnStepClickListener mCallback;
    private Context mContext;
    private List<RecepieStepDetails> mRecepieSteps;
    private String mRecepieItem;
    private ListItemClickListener mOnClickListener;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public MyViewHolder(View v) {
            super(v);
//            v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnClick
//                }
//            });
            textView = (TextView) v.findViewById(R.id.recepie_recycler_view_text) ;

        }



    }

    public interface ListItemClickListener {
        void onItemClick(int position);
    }



    public RecepieStepsAdapter(Context context, List<RecepieStepDetails> recepieSteps, String recepieItem,
                               RecepieStepsFragments.OnStepClickListener mCallback) {
        mContext = context;
        mRecepieSteps = recepieSteps;
        mRecepieItem = recepieItem;
        this.mCallback = mCallback;
    }

    public void setRecepieSteps(List<RecepieStepDetails> recepieStepDetails) {
        Log.d("SETDATA", "SETTING DATA");
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
//                Intent intent = new Intent(mContext, RecepieDetailsActivity.class);
//                //intent.putExtra("recStpDetails",  mRecepieSteps.get(i));
//                intent.putExtra("recepieItem",  mRecepieItem);
//                intent.putExtra("navigationIndex", i);
//                mContext.startActivity(intent);
                mCallback.onStepClicked(i);
                Log.d("CLICKED", "Button"+String.valueOf(i));

               // mOnClickListener.onItemClick(i);
            }
        });
    }



    @Override
    public int getItemCount() {
        if(mRecepieSteps == null) return 0;
        return mRecepieSteps.size();
    }

    public List<RecepieStepDetails> getDetails(){
        return mRecepieSteps;
    }

    public void setmOnClickListener(ListItemClickListener itemClickListeener){
        this.mOnClickListener = itemClickListeener;
    }

}

package com.example.a60010743.bakingpro.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a60010743.bakingpro.MainActivity;
import com.example.a60010743.bakingpro.R;
import com.example.a60010743.bakingpro.RecepieDetailsActivity;
import com.example.a60010743.bakingpro.RecepieStepsActivity;
import com.example.a60010743.bakingpro.model.RecepieDetails;
import com.example.a60010743.bakingpro.model.RecepieIngredients;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class RecepieAdapter extends RecyclerView.Adapter<RecepieAdapter.ItemHolder> {
    private Context mContext;
    private List<String> mRecepieNames;
    private List<RecepieDetails> mRecepieDetails;
    private boolean mTwoPane;


    public static class ItemHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        ImageView itemImage;
        ConstraintLayout itemContainer;
        public ItemHolder(View itemView) {
            super(itemView);
            itemName  = (TextView) itemView.findViewById(R.id.itemName);
            itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
            itemContainer = (ConstraintLayout) itemView.findViewById(R.id.recepieItemContainer);
        }
    }

    public RecepieAdapter(Context context, List<RecepieDetails> recepieDetails, boolean twoPane) {
        mContext = context;
        mRecepieDetails = recepieDetails;
        mTwoPane = twoPane;
    }


//    public void setRecepieItems(List<String> recepieItems) {
//        for(String s:recepieItems) {
//            Log.d(TAG, "INSIDE ADAPTER"+s);
//        }
//        mRecepieNames = recepieItems;
//        notifyDataSetChanged();
//    }
    public void setRecepieItems(List<RecepieDetails> recepieDetails) {
//        for(String s:recepieDetails) {
//            Log.d(TAG, "INSIDE ADAPTER"+s);
//        }
        mRecepieDetails = recepieDetails;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if(mRecepieDetails == null) return 0;
        return mRecepieDetails.size();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View tv = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recepie_item_view_layout, parent, false);
        RecepieAdapter.ItemHolder vh = new RecepieAdapter.ItemHolder(tv);
        return vh;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder,final int position) {
        holder.itemName.setText(mRecepieDetails.get(position).getRecepieItem());
        if(! TextUtils.isEmpty(mRecepieDetails.get(position).getImage())) {
            Picasso.with(mContext).load(mRecepieDetails.get(position).getImage()).into(holder.itemImage);
        } else {
            Picasso.with(mContext).load(R.drawable.example_appwidget_preview).into(holder.itemImage);
        }
        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(mContext, RecepieStepsActivity.class);
                intent.putExtra( "twoPane", mTwoPane);
                intent.putExtra("recepieItem", mRecepieDetails.get(position).getRecepieItem().toString());
                mContext.startActivity(intent);
            }
        });
    }

}

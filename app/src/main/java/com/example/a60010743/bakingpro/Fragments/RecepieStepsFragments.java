package com.example.a60010743.bakingpro.Fragments;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.carrier.CarrierMessagingService;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.a60010743.bakingpro.Adapters.RecepieIngAdapter;
import com.example.a60010743.bakingpro.Adapters.RecepieStepsAdapter;
import com.example.a60010743.bakingpro.R;
import com.example.a60010743.bakingpro.Utilities.JsonParseUtils;
import com.example.a60010743.bakingpro.model.RecepieIngredients;
import com.example.a60010743.bakingpro.model.RecepieStepDetails;
import com.example.a60010743.bakingpro.model.RecepieViewModel;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;


public class RecepieStepsFragments extends Fragment  implements
            RecepieStepsAdapter.ListItemClickListener{

    private RecyclerView mRecyclerView;
    private RecepieStepsAdapter mAdapter;
    private RecyclerView.LayoutManager mStepsLayoutManager;
    private RecepieViewModel mRecepieViewModel;

    private RecyclerView mRecIngView;
    private RecyclerView.Adapter mIngAdapter;
    private RecyclerView.LayoutManager mIngLayoutManager;
    private List<RecepieStepDetails> mRecepieStepDetails;
    private Button favButton;
    private boolean favBtnPressed = false;
    private String mRecepieItem;
    public static final String RECEPIE_STEP_DETAILS = "recepie_step_details";
    public static final String RECEPIE_ITEM = "recepie_item";
    public static final String VIEW_MODEL = "view_model";

    OnStepClickListener mCallback;

    @Override
    public void onItemClick(int position) {
        mCallback.onStepClicked(position);
    }

    public interface OnStepClickListener {
        void onStepClicked(int navigationIndex);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString()
                            + "must implement OnStepClickListener");
        }
    }



    public RecepieStepsFragments() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recepie_steps_fragment_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView resIngRecyclerview = (RecyclerView) view.findViewById(R.id.IngRecyclerView);
        final RecyclerView resStpRecyclerView = (RecyclerView) view.findViewById(R.id.recepieStepsRv);
        favButton = (Button) view.findViewById(R.id.fav_button);
        Intent intent = getActivity().getIntent();
        mRecepieItem = intent.getStringExtra("recepieItem");
        Log.d("RecepieStepsActivity", "recepieItem"+ mRecepieItem);

        mStepsLayoutManager = new LinearLayoutManager(getContext());
        resStpRecyclerView.setLayoutManager(mStepsLayoutManager);
        mAdapter = new RecepieStepsAdapter(getContext(), null,
                mRecepieItem, mCallback);
        resStpRecyclerView.setAdapter(mAdapter);
        //mAdapter.setmOnClickListener();
        mIngLayoutManager = new LinearLayoutManager(getContext());
        resIngRecyclerview.setLayoutManager(mIngLayoutManager);
        mRecepieViewModel = ViewModelProviders.of(this).get(RecepieViewModel.class);

        mRecepieViewModel.getIngredients(mRecepieItem).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                List<RecepieIngredients> recepieIngredients;
                try {
                    recepieIngredients = JsonParseUtils.parseIngData(s);
                    mIngAdapter = new RecepieIngAdapter(getContext(),
                            recepieIngredients);
                    // List<String> ingData = convertToStringList(recepieIngredients);

                    //displayIngredients();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resIngRecyclerview.setAdapter(mIngAdapter);
            }
        });

        mRecepieViewModel.getRecepieSteps(mRecepieItem).observe(this, new Observer<String>() {
            List<RecepieStepDetails> recepieStepDetails;
            @Override
            public void onChanged(@Nullable String s) {
                try {
                    recepieStepDetails = JsonParseUtils.parseRecSteps(s);
                    mAdapter = new RecepieStepsAdapter(getContext(), recepieStepDetails,
                            mRecepieItem, mCallback);

                    List<RecepieStepDetails> sd = mAdapter.getDetails();
                    for(RecepieStepDetails res : sd){
                        Log.d("Step Details - desc", "msg"+res.getDesc());
                        Log.d("Step Details-VURL", "msg"+res.getVideoUrl());
                    }
                    //displayIngredients();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resStpRecyclerView.setAdapter(mAdapter);
//                mAdapter.setRecepieSteps(recepieStepDetails);


            }

        });

        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favBtnPressed == false) {
                    mRecepieViewModel.updateFavouriteRecItem(mRecepieItem, true);
                    Log.d(TAG, mRecepieItem + true + "Favourite data written to DB");
                    favButton.setText("Marked Fav");
                    favButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    favBtnPressed = true;
                } else {
                    mRecepieViewModel.updateFavouriteRecItem(mRecepieItem, false);
                    Log.d(TAG, mRecepieItem + false + "UnFavourite data written to DB");
                    favButton.setText("Mark as Fav");
                    favButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    favBtnPressed = false;
                }
            }
        });



    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // Will Take care later....
       // outState.putStringArrayList(RECEPIE_STEP_DETAILS, List<RecepieStepDetails> mRecepieStepDetails);
    }

    public void setRecepieStepDetails(List<RecepieStepDetails> recepieStepDetails) {
        mAdapter.setRecepieSteps(recepieStepDetails);
    }

    public void setRecepieViewModel(RecepieViewModel recepieViewModel) {
        mRecepieViewModel = recepieViewModel;
    }

    public void setRecepieItem(String recepieItem) {
        mRecepieItem = recepieItem;
    }

}

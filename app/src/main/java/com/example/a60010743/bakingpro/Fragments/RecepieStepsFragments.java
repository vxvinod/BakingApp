package com.example.a60010743.bakingpro.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.a60010743.bakingpro.Widget.UpdateWidgetService;
import com.example.a60010743.bakingpro.model.RecepieIngredients;
import com.example.a60010743.bakingpro.model.RecepieStepDetails;
import com.example.a60010743.bakingpro.model.RecepieViewModel;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.constraint.Constraints.TAG;


public class RecepieStepsFragments extends Fragment  implements
            RecepieStepsAdapter.ListItemClickListener{

    @BindView(R.id.ingRecyclerView) RecyclerView resIngRecyclerview;
    @BindView(R.id.recepieStepsRv) RecyclerView resStpRecyclerView;
    private RecepieStepsAdapter mAdapter;
    private RecyclerView.LayoutManager mStepsLayoutManager;
    private RecepieViewModel mRecepieViewModel;
    private RecyclerView.Adapter mIngAdapter;
    private RecyclerView.LayoutManager mIngLayoutManager;
    @BindView(R.id.fav_button) Button mFavButton;
    private boolean favBtnPressed = false;
    private String mRecepieItem;
    private List<RecepieIngredients> mRecepieIngredients;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.recepie_steps_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        Intent intent = getActivity().getIntent();
        if(savedInstanceState != null) {
            mRecepieItem = savedInstanceState.getString("recepieItem");
            Log.d("SCREEN TURNED", mRecepieItem);
        }
        if(mRecepieItem == null) { mRecepieItem = intent.getStringExtra("recepieItem"); }

        mStepsLayoutManager = new LinearLayoutManager(getContext());
        resStpRecyclerView.setLayoutManager(mStepsLayoutManager);
        mAdapter = new RecepieStepsAdapter(getContext(), null,
                mRecepieItem, mCallback);
        resStpRecyclerView.setAdapter(mAdapter);

        mIngLayoutManager = new LinearLayoutManager(getContext());
        resIngRecyclerview.setLayoutManager(mIngLayoutManager);
        mRecepieViewModel = ViewModelProviders.of(this).get(RecepieViewModel.class);

        getIngredients();

        getRecepieSteps();

        mFavButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favBtnPressed == false) {
                    mRecepieViewModel.updateFavouriteRecItem(mRecepieItem, true);
                    UpdateWidgetService.startAddWidgetData(getContext(), mRecepieIngredients,
                                                            mRecepieItem);
                    mFavButton.setText("Marked Fav");
                    mFavButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    favBtnPressed = true;
                } else {
                    mRecepieViewModel.updateFavouriteRecItem(mRecepieItem, false);
                    mFavButton.setText("Mark as Fav");
                    mFavButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    favBtnPressed = false;
                }
            }
        });
    }

    private void getRecepieSteps() {
        mRecepieViewModel.getRecepieSteps(mRecepieItem).observe(this, new Observer<String>() {
            List<RecepieStepDetails> recepieStepDetails;
            @Override
            public void onChanged(@Nullable String s) {
                try {
                    recepieStepDetails = JsonParseUtils.parseRecSteps(s);
                    mAdapter = new RecepieStepsAdapter(getContext(), recepieStepDetails,
                            mRecepieItem, mCallback);
                    List<RecepieStepDetails> sd = mAdapter.getDetails();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resStpRecyclerView.setAdapter(mAdapter);
            }
        });
    }

    private void getIngredients() {
        mRecepieViewModel.getIngredients(mRecepieItem).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                try {
                    Log.d("INSIDE GET INSTANCE", s);
                    mRecepieIngredients = JsonParseUtils.parseIngData(s);
                    mIngAdapter = new RecepieIngAdapter(getContext(),
                            mRecepieIngredients);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                resIngRecyclerview.setAdapter(mIngAdapter);
            }
        });
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("recepieItem", mRecepieItem);
    }

    public void setRecepieStepDetails(List<RecepieStepDetails> recepieStepDetails) {
        mAdapter.setRecepieSteps(recepieStepDetails);
    }

    public void setRecepieViewModel(RecepieViewModel recepieViewModel) {
        mRecepieViewModel = recepieViewModel;
    }

    public void setRecepieItem(String recepieItem) {
        Log.d("INSIDE SET RECEPIE ITEM", recepieItem);
        mRecepieItem = recepieItem;
    }

}

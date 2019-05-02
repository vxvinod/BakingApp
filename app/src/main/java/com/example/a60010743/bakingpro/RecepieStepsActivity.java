package com.example.a60010743.bakingpro;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.a60010743.bakingpro.Fragments.RecepieDetailsFragments;
import com.example.a60010743.bakingpro.Fragments.RecepieStepsFragments;
import com.example.a60010743.bakingpro.model.RecepieViewModel;

public class RecepieStepsActivity extends AppCompatActivity implements
        RecepieStepsFragments.OnStepClickListener{

    private final String TAG = "RecepieStepsActivity";
    private RecepieViewModel mRecepieViewModel;
    private String mRecepieItem;
    private RecepieStepsFragments mRecepieStepsFragments;
    private boolean mTwoPane = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepie_steps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.step_toolbar);
        setSupportActionBar(toolbar);
        mTwoPane = getIntent().getExtras().getBoolean(getString(R.string.twoPane));
        mRecepieItem = getIntent().getStringExtra(getString(R.string.recepieItem));
        if(mTwoPane == true) {
            // MasterView Layout
            RecepieDetailsFragments recepieDetailsFragments = new RecepieDetailsFragments();
            recepieDetailsFragments.setRecepieItem(mRecepieItem);
            recepieDetailsFragments.setNavigationIndex(getIntent().
                    getIntExtra(getString(R.string.navigationIndex),1));
            FragmentManager detailsFragmentManager = getSupportFragmentManager();
            detailsFragmentManager.beginTransaction()
                    .add(R.id.recepie_details_fragment_container, recepieDetailsFragments)
                    .commit();
            } else {
            // Mobile View
            mRecepieStepsFragments = new RecepieStepsFragments();
            Intent intent = getIntent();
            mRecepieItem = intent.getStringExtra(getString(R.string.recepieItem));

            mRecepieStepsFragments.setRecepieItem(mRecepieItem);
            mRecepieViewModel = ViewModelProviders.of(this).get(RecepieViewModel.class);
            mRecepieStepsFragments.setRecepieViewModel(mRecepieViewModel);

            // Initiate Recepie Step Fragment container
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recepie_steps_fragment_container, mRecepieStepsFragments)
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   onBackPressed();
            }
        });
    }



    @Override
    public void onStepClicked(int navigationIndex) {
        if(mTwoPane) {
            // Update RecepieDetails fragment in MasterView Layout.
            RecepieDetailsFragments recepieDetailsFragments = new RecepieDetailsFragments();
            recepieDetailsFragments.setRecepieItem(mRecepieItem);
            recepieDetailsFragments.setNavigationIndex(navigationIndex);
            FragmentManager detailsFragmentManager = getSupportFragmentManager();
            detailsFragmentManager.beginTransaction()
                    .replace(R.id.recepie_details_fragment_container, recepieDetailsFragments)
                    .commit();
        } else {
            // Initiate Recepie Details Activity for Mobile view
            Bundle b = new Bundle();
            b.putInt(getString(R.string.navigationIndex), navigationIndex);
            b.putString(getString(R.string.recepieItem), mRecepieItem);
            final Intent intent = new Intent(this, RecepieDetailsActivity.class);
            intent.putExtras(b);
            this.startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(getString(R.string.twoPane), mTwoPane);
        outState.putString(getString(R.string.recepieItem), mRecepieItem);

    }
}
